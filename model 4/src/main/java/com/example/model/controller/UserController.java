

package com.example.model.controller;


import com.example.model.repository.UserRepository;
import com.example.model.veri.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

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
    public String addUser(@ModelAttribute User user,Model model) {
        String username = user.getUsername();


        User existingUser = userRepository.findByUsername(username);
        if (existingUser != null) {
            model.addAttribute("error", "Bu kullanıcı adı zaten kullanılıyor. Lütfen başka bir kullanıcı adı seçin.");
            return "redirect:/signup.html"; // Kullanıcıyı kayıt formuna geri yönlendir
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
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

    @GetMapping("/welcome")
    public String showWelcomePage(Model model) {
        String username = (String) model.getAttribute("username");
        model.addAttribute("username", username);
        logger.info("Hoş geldiniz sayfası gösteriliyor");
        return "welcome.html";
    }
}
