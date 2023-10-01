package com.freelancerSeeker.freelancerSeeker.controllers;

import com.freelancerSeeker.freelancerSeeker.Entity.UserSiteEntity;
import com.freelancerSeeker.freelancerSeeker.Repository.PostsRepository;
import com.freelancerSeeker.freelancerSeeker.Repository.UserSiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;

@Controller
public class ProfileController {
    @Autowired
    UserSiteRepository userSiteRepo;

    @GetMapping("/profile/{username}")
    public String getUserInfo(Model m, Principal p, @PathVariable String username) {
        if (p != null) {
            UserSiteEntity userSite = userSiteRepo.findByUsername(username);
            m.addAttribute("user", userSite);
            m.addAttribute("post", userSite.getPosts());
            return "profile";

        }
        return "home";
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
