package online_store_api.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import online_store_api.entities.Role;
import online_store_api.entities.User;
import online_store_api.global.GlobalData;
import online_store_api.repository.RoleRepository;
import online_store_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginController {
//    PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/login")
    public String login() {
        GlobalData.cart.clear();
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

//    @GetMapping("/register")
//    public String signupToRegisterPage() {
//        return "redirect:/register";
//    }

    @PostMapping("/register")
    public String signup(@ModelAttribute("user") User user, HttpServletRequest request) throws ServletException {
        String password = user.getPassword();
//        user.setPassword(passwordEncoder.encode(password));
        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.findById(2).get());
        user.setRoles(roles);
        userRepository.save(user);
        request.login(user.getEmail(), password);
        return "redirect:/adminHome";
    }
}
