package com.freelancerSeeker.freelancerSeeker.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table
public class PostsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "subject", nullable = false)
    private String subject;
    @Column(name = "body", length = 5000, nullable = false)
    private String body;
    @Column(name = "createdAd")
    private LocalDate createdAt;
    @Column(name = "startDate", nullable = false)
    private LocalDate startDate;
    @Column(name = "endDate")
    private LocalDate endDate;
    @ManyToOne
    private UserSiteEntity user;
    @ManyToMany
    @JoinTable(
            name = "post_tag",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<TagsEntity> tags = new HashSet<>();
    @OneToMany(mappedBy = "posts",cascade = CascadeType.ALL)
    private List<CommentEntity> comments;


}
