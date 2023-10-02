package com.freelancerSeeker.freelancerSeeker.Repository;

import com.freelancerSeeker.freelancerSeeker.Entity.PostsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.security.auth.Subject;
import java.util.List;

public interface PostsRepository extends JpaRepository<PostsEntity,Long> {
    List<PostsEntity> findByUserId(Long userId);
    List<PostsEntity> findAllByOrderBySubjectDesc();
    List<PostsEntity> findBySubject(String subject);
    List<PostsEntity> findBySubjectOrderBySubjectDesc(String subject);
    List<PostsEntity> findBySubjectOrderByCreatedAtDesc(String subject);
    List<PostsEntity> findBySubjectContainingOrderByCreatedAtDesc(String keyword);


}
