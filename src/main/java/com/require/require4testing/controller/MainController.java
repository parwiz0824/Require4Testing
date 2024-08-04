package com.require.require4testing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.require.require4testing.model.Test;
import com.require.require4testing.model.User;
import com.require.require4testing.repository.TestRepository;
import com.require.require4testing.repository.UserRepository;
import com.require.require4testing.service.TestService;
import com.require.require4testing.service.UserService;

import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MainController {

    @Autowired
    private UserService userService;
    @Autowired
    private TestService testService;
    @Autowired
    private TestRepository testRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String home(Model model) {
        return "index";
    }

    @GetMapping("/testmanager")
    public String showAllTests(Model model) {
        List<Test> tests = testRepository.findAll();
        List<User> allUsers = userRepository.findAll();

        List<User> testers = allUsers.stream()
                .filter(user -> "Tester".equals(user.getRole()))
                .collect(Collectors.toList());

        model.addAttribute("tests", tests);
        model.addAttribute("users", testers);
        return "testmanager";
    }

    @PostMapping("/assignUser")
    public String assignUser(@RequestParam Long testId, @RequestParam Long userId, Model model) {
        Test test = testRepository.findById(testId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);
        if (test != null && user != null) {
            test.setUser(user);
            testRepository.save(test);
        }
        return "redirect:/testmanager";
    }

    @GetMapping("/status")
    public String getStatusPage(@RequestParam(required = false) Boolean statusFilter, Model model) {
        List<Test> tests;
        if (statusFilter == null) {
            tests = testRepository.findAll(); // No filter, fetch all records
        } else {
            tests = testRepository.findByStatus(statusFilter); // Filter by status
        }
        model.addAttribute("tests", tests);
        model.addAttribute("statusFilter", statusFilter); // Add filter value to the model
        return "status";
    }

    @GetMapping("/tester")
    public String testerPage(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login"; // Redirect to login if no user is logged in
        }
        model.addAttribute("username", loggedInUser.getUsername());
        return "tester";
    }

    @GetMapping("/testsByTester")
    public String getTestsByTester(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/login"; // Redirect to login if no user is logged in
        }
        List<Test> tests = testRepository.findByUserUsername(loggedInUser.getUsername()); // Fetch tests assigned to the
                                                                                          // logged-in user
        model.addAttribute("tests", tests);
        return "testsByTester";
    }

    @GetMapping("/editTest")
    public String editTestPage(@RequestParam Long testId, Model model) {
        Test test = testRepository.findById(testId).orElse(null);
        if (test != null) {
            model.addAttribute("test", test);
            return "editTest";
        }
        return "redirect:/testsByTester";
    }

    @PostMapping("/updateTestAnswer")
    public String updateTestAnswer(@RequestParam Long testId, @RequestParam String answer) {
        Test test = testRepository.findById(testId).orElse(null);
        if (test != null) {
            test.setAnswer(answer);
            test.setStatus(true); // Set status to true when the answer is updated
            testRepository.save(test);
        }
        return "redirect:/testsByTester";
    }

    @GetMapping("/testfallersteller")
    public String showTestCreationForm(Model model) {
        model.addAttribute("test", new Test());
        model.addAttribute("message", "");
        return "testfallersteller";
    }

    @PostMapping("/testfallersteller")
    public String createTest(@ModelAttribute Test test, Model model) {
        testRepository.save(test);
        model.addAttribute("test", new Test());
        model.addAttribute("message", "Testfrage wurde erfolgreich erstellt!");
        return "testfallersteller";
    }
}