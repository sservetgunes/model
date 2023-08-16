



package com.example.model.controller;


import com.example.model.repository.RoleRepository;
import com.example.model.repository.UserRepository;
import com.example.model.veri.Role;
import com.example.model.veri.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class UserController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private RoleRepository RoleRepository;

    @Autowired
    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/")
    public String showHomePage(Model model) {
        logger.info("Ana sayfa gösteriliyor");
        return "index.html";
    }

    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        logger.info("Kayıt formu gösteriliyor");
        model.addAttribute("user", new User());
        return "signup.html";
    }

    @PostMapping("/signup")
    public String addUser(@ModelAttribute User user, Model model) {
        String username = user.getUsername();


        User existingUser = userRepository.findByUsername(username);
        if (existingUser != null) {
            model.addAttribute("error", "Bu kullanıcı adı zaten kullanılıyor. Lütfen başka bir kullanıcı adı seçin.");
            return "redirect:/signup.html";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role userRole = RoleRepository.findByName("ROLE_USER");
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        user.setRoles(roles);
        userRepository.save(user);
        logger.info("Yeni kullanıcı kaydedildi: {}", user.getUsername());
        return "redirect:/login.html";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        logger.info("Giriş formu gösteriliyor");
        model.addAttribute("loginUser", new User());
        return "login.html";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute User loginUser, Model model) {
        User user = userRepository.findByUsername(loginUser.getUsername());

        if (user != null && passwordEncoder.matches(loginUser.getPassword(), user.getPassword())) {

            model.addAttribute("username", user.getUsername());
            logger.info("Kullanıcı giriş yaptı: {}", user.getUsername());
            return "redirect:/welcome.html";
        } else {

            logger.warn("Kullanıcı giriş yapamadı: {}", loginUser.getUsername());
            return "redirect:/login.html";
        }
    }

   /* @GetMapping("/welcome")
    public String showWelcomePage(Model model) {
        String username = (String) model.getAttribute("username");
        model.addAttribute("username", username);
        logger.info("Hoş geldiniz sayfası gösteriliyor");
        return "welcome.html";
    }*/

    @GetMapping("/user/dashboard")
    public String showUserDashboard(Model model, Principal principal) {
        String username = principal.getName();
        model.addAttribute("username", username);
        return "user/dashboard";
    }

    @GetMapping("/admin/userList")
    public String showUserList(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "admin/userList";
    }
}