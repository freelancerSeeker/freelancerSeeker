package com.freelancerSeeker.freelancerSeeker.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class FreeLancer implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "username",nullable = false)
    private String username;
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("FREELANCER"));
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
