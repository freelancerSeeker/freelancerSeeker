package com.freelancerSeeker.freelancerSeeker.controllers;


import com.freelancerSeeker.freelancerSeeker.Entity.UserSiteEntity;
import com.freelancerSeeker.freelancerSeeker.Repository.UserSiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class ChatPageController {
    @Autowired
    public UserSiteRepository userSiteRepository;

    @GetMapping("/chat")
    public String chatPage(@RequestParam("receiverId") Long receiverId, Model model, Principal principal) {
        UserSiteEntity sender=userSiteRepository.findByUsername(principal.getName());



        UserSiteEntity receiver=userSiteRepository.findById(receiverId).orElse(null);
        if (receiver != null) {

            model.addAttribute("receiver", receiver);
            model.addAttribute("sender", sender);
            return "chat";
        } else {

            return "redirect:/error";
        }

    }
}
