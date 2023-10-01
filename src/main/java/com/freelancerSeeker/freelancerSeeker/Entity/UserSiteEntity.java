package com.freelancerSeeker.freelancerSeeker.Entity;

import com.freelancerSeeker.freelancerSeeker.Enum.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class UserSiteEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private  Long id;
    @Column(name = "username",unique = true,nullable = false)
    private String username;
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
    private List<PostsEntity> posts;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<ContractEntity> contracts;
    @Enumerated(EnumType.ORDINAL)
    private Role roles;
    @OneToMany(mappedBy = "usersite",cascade = CascadeType.ALL)
    private List<SkillsEntity> skillsList;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
