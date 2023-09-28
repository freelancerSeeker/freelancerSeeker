package com.freelancerSeeker.freelancerSeeker.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "subject", nullable = false)
    private String subject;
    @Column(name = "body", length = 5000, nullable = false)
    private String body;
    @Column(name = "createdAd")
    private Date createdAt;
    @Column(name = "startDate",nullable = false)
    private Date startDate;
    @Column(name = "endDate")
    private Date  endDate;
    @ManyToOne
    private NormalUser user;
    @ManyToOne
    private FreeLancer freeLancer;
}
