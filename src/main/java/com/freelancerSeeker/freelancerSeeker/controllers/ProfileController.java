package com.freelancerSeeker.freelancerSeeker.controllers;

import com.freelancerSeeker.freelancerSeeker.Entity.SkillsEntity;
import com.freelancerSeeker.freelancerSeeker.Entity.UserSiteEntity;
import com.freelancerSeeker.freelancerSeeker.Enum.Role;
import com.freelancerSeeker.freelancerSeeker.Repository.PostsRepository;
import com.freelancerSeeker.freelancerSeeker.Repository.SkillsRepository;
import com.freelancerSeeker.freelancerSeeker.Repository.UserSiteRepository;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProfileController {
    @Autowired
    UserSiteRepository userSiteRepo;

    @GetMapping("/profile/{username}")
    public String getUserInfo(Model m, Principal p, @PathVariable String username) {
        UserSiteEntity userSite = userSiteRepo.findByUsername(username);
        if(userSite!=null && p!=null)
        {
                String logedUser= p.getName();

            m.addAttribute("user", userSite);
            m.addAttribute("post", userSite.getPosts());
            m.addAttribute("loggedUsername", logedUser);
            return "profile";

        }
        return "redirect:/";
    }

    @PutMapping("/freelancer/{id}")

    public RedirectView updateFreeLancerInfo(@PathVariable Long id, @RequestParam String username, @RequestParam String email, @RequestParam String firstName, @RequestParam String lastName, @RequestParam String description, @RequestParam String phoneNumber,@RequestParam String country,@RequestParam List<String> skills) {
        UserSiteEntity userSite= userSiteRepo.findById(id).orElseThrow();

        userSite.setUsername(username);
        userSite.setCountry(country);
        userSite.setEmail(email);
        userSite.setFirstname(firstName);
        userSite.setLastname(lastName);
//        userSite.setSkillsList(getSkillsByKeyword(keyword);
        userSiteRepo.save(userSite);
        return new RedirectView("/freelancer/" + id);
    }



    @PutMapping("/users/{id}")

    public RedirectView updateNormalUserInfo(@PathVariable Long id, @RequestParam String firstname, @RequestParam String lastname, @RequestParam String description, @RequestParam String email, @RequestParam String country) {

        UserSiteEntity existingUser = userSiteRepo.findById(id).orElseThrow();

        existingUser.setDescription(description);
        existingUser.setCountry(country);
        existingUser.setEmail(email);
        existingUser.setFirstname(firstname);
        existingUser.setLastname(lastname);
        userSiteRepo.save(existingUser);
        return new RedirectView("/profile/" + existingUser.getUsername());
    }}



    @PostMapping("/user/skill/{id}")
    public RedirectView updateFreeLancerSkill(@PathVariable Long id,@RequestParam String skillName) {
        UserSiteEntity user = userSiteRepo.findById(id).orElseThrow();
        SkillsEntity skill = new SkillsEntity();
        skill.setSkill(skillName);
        skill.setUsersite(user);
        user.getSkillsList().add(skill);
        userSiteRepo.save(user);
        return new RedirectView("/profile/" + user.getUsername());
    }










