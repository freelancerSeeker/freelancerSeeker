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
public class FreeLancer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "displayName",nullable = false)
    private String displayName;
    @Column(name = "password",nullable = false)
    private String password;
    @Column(name = "email",unique = true,nullable = false)
    private String email;
    @Column(name = "firstName",nullable = false)
    private String firstname;
    @Column(name = "lastName",nullable = false)
    private String lastname;
    @OneToMany(mappedBy = "freeLancerUser",cascade = CascadeType.ALL)
    private List<Skills> skillsList;
    @OneToMany(mappedBy = "freeLancer")
    private List<Posts> posts;
    @OneToMany(mappedBy = "freeLancer")
    private List<Contract> contracts;
}
