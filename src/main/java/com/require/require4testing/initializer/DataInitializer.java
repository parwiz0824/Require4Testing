package com.require.require4testing.initializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.require.require4testing.model.User;
import com.require.require4testing.service.UserService;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) throws Exception {
        if (userService.getAllUsers().isEmpty()) {
            User testManager = new User();
            testManager.setUsername("testmanager");
            testManager.setPassword("password"); 
            testManager.setRole("TestManager");
            userService.save(testManager);

            User tester1 = new User();
            tester1.setUsername("tester1");
            tester1.setPassword("password"); 
            tester1.setRole("Tester");
            userService.save(tester1);

            User tester2 = new User();
            tester2.setUsername("tester2");
            tester2.setPassword("password"); 
            tester2.setRole("Tester");
            userService.save(tester2);
                 
            User tester3 = new User();
            tester3.setUsername("tester3");
            tester3.setPassword("password"); 
            tester3.setRole("Tester");
            userService.save(tester3);

            User Testfallersteller = new User();
            Testfallersteller.setUsername("testfallersteller");
            Testfallersteller.setPassword("password"); // You might want to hash the password
            Testfallersteller.setRole("Testfallersteller");
            userService.save(Testfallersteller);
        }
    }
}
