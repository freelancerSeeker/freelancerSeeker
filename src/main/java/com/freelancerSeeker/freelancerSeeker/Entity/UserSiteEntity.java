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
@Table
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
    @Column(name = "country")
    private String country;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<PostsEntity> posts;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<ContractEntity> contracts;
    @OneToMany(mappedBy = "approvedBy",cascade = CascadeType.ALL)
    private List<ContractEntity> contractsForApprove;
    @Enumerated(EnumType.ORDINAL)
    private Role roles;
    @OneToMany(mappedBy = "usersite",cascade = CascadeType.ALL)
    private List<SkillsEntity> skillsList;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<CommentEntity> comments;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<ReviewEntity> reviews;
    @ManyToMany
    @JoinTable(name = "followers_table",
            joinColumns = {@JoinColumn(name = "userId")}, inverseJoinColumns = {@JoinColumn(name = "followerId")})
    private Set<UserSiteEntity> following = new HashSet<>();
    @ManyToMany(mappedBy = "following")
    private Set<UserSiteEntity> followers = new HashSet<>();

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

    public void addFollowing(UserSiteEntity user) {
        this.following.add(user);
    }
    public void addFollower(UserSiteEntity user) {
        this.followers.add(user);
    }
}