package com.freelancerSeeker.freelancerSeeker.Repository;

import com.freelancerSeeker.freelancerSeeker.Entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<CommentEntity,Long> {
}
