package com.freelancerSeeker.freelancerSeeker.Repository;

import com.freelancerSeeker.freelancerSeeker.Entity.UserSiteEntity;
import com.freelancerSeeker.freelancerSeeker.Enum.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserSiteRepository extends JpaRepository<UserSiteEntity, Long> {
    UserSiteEntity findByUsername(String username);

    UserSiteEntity findByEmail(String email);

    List<UserSiteEntity> findByRoles(Role freelancer);

    List<UserSiteEntity> findByUsernameContainingIgnoreCaseAndRoles(String query, Role freelancer);
}
