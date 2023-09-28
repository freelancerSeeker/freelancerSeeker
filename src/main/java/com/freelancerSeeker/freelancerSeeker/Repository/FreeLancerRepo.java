package com.freelancerSeeker.freelancerSeeker.Repository;

import com.freelancerSeeker.freelancerSeeker.Models.FreeLancer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FreeLancerRepo extends JpaRepository<FreeLancer,Long> {
}
