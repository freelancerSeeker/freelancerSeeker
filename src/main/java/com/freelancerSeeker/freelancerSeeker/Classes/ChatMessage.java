package com.freelancerSeeker.freelancerSeeker.Classes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChatMessage {
    private Long senderId;
    private Long receiverId;
    private String message;
}
