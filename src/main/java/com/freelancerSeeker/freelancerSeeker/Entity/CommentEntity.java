package com.freelancerSeeker.freelancerSeeker.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "body", length = 5000, nullable = false)
    private String body;

    @ManyToOne
    private UserSiteEntity user;
    @ManyToOne
    private PostsEntity posts;


}
