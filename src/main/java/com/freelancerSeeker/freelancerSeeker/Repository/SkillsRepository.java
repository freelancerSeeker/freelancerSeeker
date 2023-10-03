package com.freelancerSeeker.freelancerSeeker.Repository;

import com.freelancerSeeker.freelancerSeeker.Entity.SkillsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SkillsRepository extends JpaRepository<SkillsEntity,Long> {
    static List<String> getSkillsByKeyword(String keyword) {
        return null;
    }
}

