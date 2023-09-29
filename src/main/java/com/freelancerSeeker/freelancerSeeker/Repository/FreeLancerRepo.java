package com.freelancerSeeker.freelancerSeeker.Repository;

import com.freelancerSeeker.freelancerSeeker.Models.FreeLancer;
import com.freelancerSeeker.freelancerSeeker.Models.NormalUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FreeLancerRepo extends JpaRepository<FreeLancer,Long> {
    FreeLancer findByUsername(String username);
}
