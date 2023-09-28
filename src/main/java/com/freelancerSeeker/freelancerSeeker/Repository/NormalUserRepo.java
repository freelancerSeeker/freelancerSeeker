package com.freelancerSeeker.freelancerSeeker.Repository;

import com.freelancerSeeker.freelancerSeeker.Models.NormalUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NormalUserRepo extends JpaRepository<NormalUser,Long> {
}
