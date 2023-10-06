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
public class ContractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "subject",nullable = false)
    private String subject;
    @Column(name = "startDate",nullable = false)
    private LocalDate startDate;
    @Column(name = "endDate",nullable = false)
    private LocalDate endDate;
    @Column(name = "pricePerHour",nullable = false)
    private double pricePerHour;
    @Column(name = "body",nullable = false)
    private String body;
    private LocalDate createdAt;
    @Column(name = "approved")
    private boolean approved = false;

    @ManyToOne
    private UserSiteEntity user;
    @ManyToOne
    private UserSiteEntity approvedBy;






}
