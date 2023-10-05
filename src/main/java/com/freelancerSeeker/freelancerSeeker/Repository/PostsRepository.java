package com.freelancerSeeker.freelancerSeeker.Repository;

import com.freelancerSeeker.freelancerSeeker.Entity.PostsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import javax.security.auth.Subject;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface PostsRepository extends JpaRepository<PostsEntity,Long> {
    List<PostsEntity> findByUserId(Long userId);

    List<PostsEntity> findAllByOrderBySubjectDesc();
    List<PostsEntity> findBySubject(String subject);


    List<PostsEntity> findBySubjectContainingOrderByCreatedAtDesc(String keyword);


    Page<PostsEntity> findBySubjectContaining(String subject, Pageable pageable);

    Page<PostsEntity> findAllByOrderByCreatedAtDesc(Pageable pageable);
    Page<PostsEntity> findByOrderByCreatedAtDesc(Date createdAt,Pageable pageable);
    List<PostsEntity> findByCreatedAtContaining(LocalDate createdAt);

    Page<PostsEntity> findBySubjectContainingAndCreatedAtContaining(String subject, LocalDate createdAt, Pageable pageable);



    List<PostsEntity> findByCreatedAt(LocalDate createdAt);


    Page<PostsEntity> findBySubjectContainingAndStartDate(String subject, LocalDate startDate, Pageable pageable);
}


