package com.freelancerSeeker.freelancerSeeker.controllers;

import com.freelancerSeeker.freelancerSeeker.Entity.MessageEntity;
import com.freelancerSeeker.freelancerSeeker.Repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController

public class ChatController {

    @Autowired
    MessageRepository messageRepository;
   @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

   @MessageMapping("/chat/send")
    public void sendMessage(MessageEntity message)
   {
        MessageEntity message1= new MessageEntity();

        message1.setSender(message.getSender());
        message1.setReceiver(message.getReceiver());
        message1.setContent(message.getContent());
        message1.setCreatedAt(LocalDate.now());
       messageRepository.save(message1);

       simpMessagingTemplate.convertAndSendToUser(message.getReceiver().getUsername(),"/topic/chat",message1);

   }

}
