package com.example.braincorp;

import com.example.braincorp.model.UserGroup;
import com.example.braincorp.model.User;
import com.example.braincorp.repository.UserGroupRepository;
import com.example.braincorp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

@SpringBootApplication
public class PasswdAsAService implements CommandLineRunner {

    static String passwordFilePath = "/etc/passwd";
    static String groupsFilePath = "/etc/group";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserGroupRepository userGroupRepository;

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        confirmAndValidatePath("passwd", passwordFilePath, scan);
        confirmAndValidatePath("group", groupsFilePath, scan);
        scan.close();
        SpringApplication.run(PasswdAsAService.class, args);
    }

    static void confirmAndValidatePath(String type, String path, Scanner scan){
        System.out.println("Do you want to customize '"+ type +"' file path? (Defaults to : '/etc/" + type + "')\n" +
                "Y/N");
        String input = scan.nextLine();

        while(!input.equalsIgnoreCase("Y") & !input.equalsIgnoreCase("N")){
            System.out.print("\nPlease enter a valid choice between Y or N : ");
            input = scan.nextLine();
        }

        switch(input){
            case "Y":
                boolean isPathValid = false;
                do {
                    System.out.print("Enter valid file path (Remember Default is : '/etc/" + type + "')" );
                    String newPath = scan.nextLine();
                    isPathValid = validateFilePath(type, newPath);
                    if(isPathValid)
                        path = newPath;
                } while(!isPathValid);

                break;
            case "N":
                String newPath = path;
                do {
                    isPathValid = validateFilePath(type, newPath);
                    if(isPathValid) {
                        path = newPath;
                        break;
                    }
                    else {
                        System.out.print("Enter valid file path: ");
                        newPath = scan.nextLine();
                    }
                } while(!isPathValid);
                break;
        }

        System.out.println("Initializing service with '" +  type + "' at : " + path);
    }

    static boolean validateFilePath(String type, String path){
        Scanner scan = null;
        try {
            scan = new Scanner(new File(path));
            switch (type) {
                case "passwd":
                    while(scan.hasNextLine()){
                        String[] line = scan.nextLine().split(":");
                        // check if there are at least 7 parts and if uid and gid are valid numbers.
                        if(line.length != 7 || !line[2].matches("[0-9]+") || !line[3].matches("[0-9]+")){
                            System.out.println(Arrays.toString(line));
                            System.out.println("File doesn't follow valid structure each line in " + type + " " +
                                    "file must be of format: \n" +
                                    "user:passwd:uid:gid:comment:home:shell\n" +
                                    "example line in the file \n" +
                                    "tom:x:1000:1000:Vivek Gite:/home/vivek:/bin/bash\n");
                            return false;
                        }
                    }
                    passwordFilePath = path;
                    break;
                case "group":
                    while(scan.hasNextLine()){
                        String[] line = scan.nextLine().split(":");
                        // check if there are at least 4 parts and if gid is a valid number.
                        if(line.length != 4 || !line[2].matches("[0-9]+")){
                            System.out.println(Arrays.toString(line));
                            System.out.println("File doesn't follow valid structure each line in " + type + "" +
                                    "file must be of format: \n" +
                                    "name:passwd:gid:members\n" +
                                    "example line in the file \n" +
                                    "cdrom:x:24:vivek,raj,abc");
                            return false;
                        }
                    }
                    groupsFilePath = path;
                    break;
            }
        } catch (FileNotFoundException e){
            System.out.println("File not found at path, please enter valid path");
            return false;
        } finally {
            if(scan != null)
                scan.close();
        }
        return true;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scan = new Scanner(new File(this.passwordFilePath));
        while(scan.hasNextLine()){
            String[] line = scan.nextLine().split(":");
            this.userRepository.save(new User(line[0], Long.parseLong(line[2]),Long.parseLong(line[3]), line[4], line[5], line[6]));
        }
        scan.close();
        scan = new Scanner(new File(this.groupsFilePath));
        while(scan.hasNextLine()){
            String[] line = scan.nextLine().split(":");
            this.userGroupRepository.save(new UserGroup(line[0], Long.parseLong(line[2]), Arrays.asList(line[3].split(","))));
        }
        scan.close();

    }

}
