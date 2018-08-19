# Passwd As A Service
The idea of this challenge is to create a minimal HTTP service that exposes the user and group information on a UNIX-like system that is usually locked away in the UNIX /etc/passwd and /etc/groups files

## Pre-requisites: 

#### Technologies:
1) Java
2) Spring Boot
3) Maven

#### File paths with valid access.
When the application initializes, it will ask for a valid file which the application can open and follows the valid syntax as stated in the links below:
1) `/etc/passwd` - [passwd file format](https://www.cyberciti.biz/faq/understanding-etcpasswd-file-format/)
2) `/etc/groups` - [groups file format](https://www.cyberciti.biz/faq/understanding-etcgroup-file/)

On unix machines, this file already exists, in windows create a sample file or use the files in this directory provided in "etc/" and place these files in C:/etc/

## How to run:
Run the PasswdAsAService.java file.

The application boots up by validating if you want to go with the default paths for passwd and groups or customize it, for the application to proceed, it is mandatory the file's have valid structure.

#### Endpoints:
Once the application boots up, you can hit several GET endpoints:
For passwd:

`localhost:8080/users` - Return a list of all users on the system, as defined in the /etc/passwd file.

`localhost:8080/users/query[?name=<nq>][&uid=<uq>][&gid=<gq>][&comment=<cq>][&home=<hq>][&shell=<sq>]` - Return a list of users matching all of the specified query fields. The bracket notation indicates that any of the following query parameters may be supplied: name, uid, gid, comment, home, shell.

`localhost:8080/users/<uid>` - Return a single user with "uid".

`localhost:8080/users/<uid>/groups` - Return all the groups for a given user.

For groups:
`localhost:8080/groups` - Return a list of all groups on the system, a defined by /etc/group.

`localhost:8080/groups/query[?name=<nq>][&gid=<gq>][&member=<mq1>[&member=<mq2>][&...]]` - Return a list of groups matching all of the specified query fields. The bracket notation indicates that any of the following query parameters may be supplied: name, gid, members (repeated)

`localhost:8080/groups/<gid>` - Return a single group with "gid".

