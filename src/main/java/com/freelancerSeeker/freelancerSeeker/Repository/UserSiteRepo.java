package com.freelancerSeeker.freelancerSeeker.Repository;

import com.freelancerSeeker.freelancerSeeker.Models.UserSite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSiteRepo extends JpaRepository<UserSite,Long> {
    UserSite findByUsername(String username);

}
