package com.example.braincorp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.example.braincorp.PasswdAsAService.validateFilePath;
import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PasswdAsAServiceTests {

    @Test
    public void validateFilePathNegativeTest() {
        boolean result = validateFilePath("passwd", "/fake/path");
        assertThat(result).isFalse();
    }

    @Test
    public void validateFilePathPositiveTest() {
        boolean result = validateFilePath("passwd", "etc/passwd");
        assertThat(result).isTrue();
    }

}
