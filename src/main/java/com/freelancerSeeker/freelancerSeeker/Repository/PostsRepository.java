package com.freelancerSeeker.freelancerSeeker.Repository;

import com.freelancerSeeker.freelancerSeeker.Entity.PostsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostsRepository extends JpaRepository<PostsEntity,Long> {
}
