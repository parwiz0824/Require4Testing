package com.require.require4testing.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import com.require.require4testing.model.User;
import com.require.require4testing.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; 
    }

@PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session, Model model) {
        User user = userRepository.findByUsername(username);

        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("loggedInUser", user);
            if ("Tester".equals(user.getRole())) {
                return "redirect:/tester";
            } else if ("TestManager".equals(user.getRole())) {
                return "redirect:/testmanager";
            } else {
                return "redirect:/testfallersteller";
            }
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }
}