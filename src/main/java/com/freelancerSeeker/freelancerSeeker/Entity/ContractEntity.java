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
public class ContractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "subject",nullable = false)
    private String subject;
    @Column(name = "startDate",nullable = false)
    private String startDate;
    @Column(name = "endDate",nullable = false)
    private String endDate;
    @Column(name = "pricePerHour",nullable = false)
    private double pricePerHour;
    @Column(name = "body",nullable = false)
    private String body;
    private LocalDate createdAt;


    @ManyToOne
    private UserSiteEntity user;
}
