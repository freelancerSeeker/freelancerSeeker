package com.freelancerSeeker.freelancerSeeker.controllers;

import com.freelancerSeeker.freelancerSeeker.Classes.ChatMessage;
import com.freelancerSeeker.freelancerSeeker.Entity.MessageEntity;
import com.freelancerSeeker.freelancerSeeker.Entity.UserSiteEntity;
import com.freelancerSeeker.freelancerSeeker.Repository.MessageRepository;
import com.freelancerSeeker.freelancerSeeker.Repository.UserSiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ChatController {

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    UserSiteRepository userSiteRepository;
    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/chat/{username}")
    // Sends the return value of this method to /topic/messages
    public void getMessages(@DestinationVariable String username, @Payload ChatMessage dto) {
        UserSiteEntity sender = userSiteRepository.findById(dto.getSenderId()).orElseThrow();
        UserSiteEntity receiver = userSiteRepository.findById(dto.getReceiverId()).orElseThrow();
        // SAVE THE MESSAGE
        MessageEntity msg = new MessageEntity();
        msg.setSender(sender);
        msg.setContent(dto.getMessage());
        msg.setReceiver(receiver);
        msg.setCreatedAt(LocalDate.now());
        messageRepository.save(msg);
        simpMessagingTemplate.convertAndSend("/topic/messages/" + username, msg);
    }
}
