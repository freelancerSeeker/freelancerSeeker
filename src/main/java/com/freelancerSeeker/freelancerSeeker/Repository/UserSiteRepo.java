package com.freelancerSeeker.freelancerSeeker.Repository;

import com.freelancerSeeker.freelancerSeeker.Entity.UserSiteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSiteRepo extends JpaRepository<UserSiteEntity,Long> {
    UserSiteEntity findByUsername(String username);


}
