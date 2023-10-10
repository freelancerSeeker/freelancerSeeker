package com.freelancerSeeker.freelancerSeeker.controllers;


import com.freelancerSeeker.freelancerSeeker.Entity.MessageEntity;
import com.freelancerSeeker.freelancerSeeker.Entity.UserSiteEntity;
import com.freelancerSeeker.freelancerSeeker.Repository.MessageRepository;
import com.freelancerSeeker.freelancerSeeker.Repository.UserSiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
public class ChatPageController {
    @Autowired
    public UserSiteRepository userSiteRepository;

    @Autowired
    public MessageRepository messageRepository;

    @GetMapping("/chat")
    public String chatPage(@RequestParam("receiverId") Long receiverId, Model model, Principal principal) {
       UserSiteEntity sender=userSiteRepository.findByUsername(principal.getName());
       UserSiteEntity receiver=userSiteRepository.findById(receiverId).orElse(null);

        List<MessageEntity> messageBetweenBothUsers = messageRepository.findMessagesBetweenUsers(sender.getId(), receiver.getId());
        if (receiver != null) {
            //RETRIEVE THE OLD MESSAGES
            List<MessageEntity> senderMsg = sender.getReceivedMessages();
            List<MessageEntity> receivedMessages = receiver.getReceivedMessages();
            model.addAttribute("receiver", receiver);
            model.addAttribute("sender", sender);
            model.addAttribute("senderMsg", messageBetweenBothUsers);
            model.addAttribute("receivedMessages", receivedMessages);
            model.addAttribute("username", sender.getUsername());
            return "chat";
        } else {

            return "redirect:/error";
        }

    }
}
