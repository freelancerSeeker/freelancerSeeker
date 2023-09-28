package com.freelancerSeeker.freelancerSeeker.Repository;

import com.freelancerSeeker.freelancerSeeker.Models.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractsRepo extends JpaRepository<Contract,Long> {
}
