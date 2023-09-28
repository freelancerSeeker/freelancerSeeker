package com.freelancerSeeker.freelancerSeeker.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class NormalUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "displayName",nullable = false)
    private String displayName;
    @Column(name = "password",nullable = false)
    private String password;
    @Column(name = "description")
    private String description;
    @Column(name = "email",unique = true,nullable = false)
    private String email;
    @Column(name = "firstName",nullable = false)
    private String firstname;
    @Column(name = "lastName",nullable = false)
    private String lastname;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Posts> posts;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Contract> contracts;
}
