package com.freelancerSeeker.freelancerSeeker.controllers;

import com.freelancerSeeker.freelancerSeeker.Enum.Role;
import com.freelancerSeeker.freelancerSeeker.Entity.UserSiteEntity;
import com.freelancerSeeker.freelancerSeeker.Repository.UserSiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/users/{id}")
    public String getUserInfo(Model m, Principal p, @PathVariable Long id) {
        if (p != null) {
            String username = p.getName();
            UserSiteEntity userSite = userSiteRepo.findByUsername(username);
            m.addAttribute("username", username);

        }
        UserSiteEntity userSite = userSiteRepo.findById(id).orElseThrow();
        m.addAttribute("user", userSite);
        return "/profile.html";
    }

    @PutMapping("/freelancer/{id}")
    public RedirectView updateFreeLancerInfo(@PathVariable Long id, @RequestParam String username, @RequestParam String email, @RequestParam String firstName, @RequestParam String lastName, @RequestParam String description, @RequestParam String phoneNumber) {
        UserSiteEntity userSite= userSiteRepo.findById(id).orElseThrow();
        userSite.setUsername(username);
        userSite.setEmail(email);
        userSite.setFirstname(firstName);
        userSite.setLastname(lastName);
        userSiteRepo.save(userSite);
        return new RedirectView("/freelancer/" + id);
    }
    @PutMapping("/users/{id}")
    public RedirectView updateNormalUserInfo(@PathVariable Long id, @RequestBody UserSiteEntity updatedUser) {
        UserSiteEntity existingUser = userSiteRepo.findById(id).orElseThrow();
        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setPassword(updatedUser.getPassword());
        existingUser.setDescription(updatedUser.getDescription());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setFirstname(updatedUser.getFirstname());
        existingUser.setLastname(updatedUser.getLastname());
        userSiteRepo.save(existingUser);
        return new RedirectView("/users/" + id);
    }

}
