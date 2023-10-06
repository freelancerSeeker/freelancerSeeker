package com.freelancerSeeker.freelancerSeeker.Repository;


import com.freelancerSeeker.freelancerSeeker.Entity.ReviewEntity;
import com.freelancerSeeker.freelancerSeeker.Entity.UserSiteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<ReviewEntity,Long>{
    List <ReviewEntity> findByFreelancer(String username);

    List<ReviewEntity> findByFreelancerUsername(String toLowerCase);

}
