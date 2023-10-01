package com.freelancerSeeker.freelancerSeeker.controllers;

import com.freelancerSeeker.freelancerSeeker.Enum.Role;
import com.freelancerSeeker.freelancerSeeker.Entity.UserSiteEntity;
import com.freelancerSeeker.freelancerSeeker.Repository.UserSiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
public class UserAuthenticationController {
    // This controller to handle both freelancer and user authentication.

    @Autowired
    UserSiteRepository userSiteRepo;
    @Autowired
    HttpServletRequest request;
    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login() {
        return "signup.html";
    }


    @GetMapping("/")
    public String getHome(Principal p , Model homeModel) {
        if (p != null){
            String username = p.getName();
            homeModel.addAttribute("username",username);
            return "home";
        }
        return "home";
    }

    @GetMapping("/works")
    public String getWorks() {
        return "works";
    }

    @GetMapping("/reviews")
    public String getReviews() {
        return "reviews";
    }

    @GetMapping("/about")
    public String getAbout() {
        return "about";
    }


    @PostMapping("/signup")
    public RedirectView signupNormalUser(@RequestParam String username, @RequestParam String password, @RequestParam String description, @RequestParam String email, @RequestParam String firstname, @RequestParam String lastname, @RequestParam String role) {
        String encryptedPassword = passwordEncoder.encode(password);
        UserSiteEntity usersite = new UserSiteEntity();
        usersite.setUsername(username);
        usersite.setPassword(encryptedPassword);
        usersite.setDescription(description);
        usersite.setEmail(email);
        usersite.setFirstname(firstname);
        usersite.setLastname(lastname);
        usersite.setRoles(Role.valueOf(role));
        userSiteRepo.save(usersite);


        System.out.println(usersite.getUsername());

        return new RedirectView("/login");

    }

    public RedirectView authWithHttpServletRequest(String username, String password) {

        try {
            request.login(username, password);
        } catch (ServletException e) {
            e.printStackTrace();
        }
        return new RedirectView("/signup");
    }

}
