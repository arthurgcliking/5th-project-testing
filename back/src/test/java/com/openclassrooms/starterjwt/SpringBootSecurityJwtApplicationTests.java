package com.openclassrooms.starterjwt;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SpringBootSecurityJwtApplicationTests {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private Environment environment;

    @Test
    public void contextLoads() {
        assertNotNull(context, "The application context should have loaded.");
    }

    @Test
    public void testActiveProfiles() {
        String[] activeProfiles = environment.getActiveProfiles();
        assertNotNull(activeProfiles, "Active profiles should not be null");
        assertTrue(activeProfiles.length > 0, "At least one active profile should be set");
    }

    @Test
    public void testBeanLoading() {
        assertTrue(context.containsBean("springBootSecurityJwtApplication"), "The main application bean should be loaded.");
    }

    @Test
    public void testDefaultProfile() {
        String[] activeProfiles = environment.getActiveProfiles();
        if (activeProfiles.length == 0) {
            String[] defaultProfiles = environment.getDefaultProfiles();
            assertArrayEquals(new String[]{"default"}, defaultProfiles, "The default profile should be 'default'.");
        }
    }
}
