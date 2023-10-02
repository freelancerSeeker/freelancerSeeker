package com.freelancerSeeker.freelancerSeeker.Repository;

import com.freelancerSeeker.freelancerSeeker.Entity.PostsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface PostsRepository extends JpaRepository<PostsEntity,Long> {
    List<PostsEntity> findByUserId(Long userId);


    Page<PostsEntity> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
