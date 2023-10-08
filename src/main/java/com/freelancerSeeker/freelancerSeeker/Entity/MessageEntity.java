package com.freelancerSeeker.freelancerSeeker.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table
public class MessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private UserSiteEntity sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private UserSiteEntity receiver;
    @Column(name = "message",length = 5000, nullable = false)
    private String message;
    @Column(name = "createdAd")
    private LocalDate createdAt;

}
