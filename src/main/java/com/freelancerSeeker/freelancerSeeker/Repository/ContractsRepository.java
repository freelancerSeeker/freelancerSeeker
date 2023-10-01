package com.freelancerSeeker.freelancerSeeker.Repository;

import com.freelancerSeeker.freelancerSeeker.Entity.ContractEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractsRepository extends JpaRepository<ContractEntity,Long> {
}
