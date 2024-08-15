package com.openclassrooms.starterjwt;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;

import static org.mockito.Mockito.mockStatic;

public class SpringBootSecurityJwtApplicationMainTest {

    @Test
    void testMain() {
        try (MockedStatic<SpringApplication> mockedStatic = mockStatic(SpringApplication.class)) {
            SpringBootSecurityJwtApplication.main(new String[]{});
            mockedStatic.verify(() -> SpringApplication.run(SpringBootSecurityJwtApplication.class, new String[]{}));
        }
    }
}
