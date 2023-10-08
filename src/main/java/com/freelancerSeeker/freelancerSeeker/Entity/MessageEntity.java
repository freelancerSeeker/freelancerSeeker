package com.freelancerSeeker.freelancerSeeker.Entity;

import lombok.*;
import javax.persistence.Id;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
    private String content;
    @Column(name = "createdAd")
    private LocalDate createdAt;
}
