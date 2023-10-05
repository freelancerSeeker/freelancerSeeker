package com.freelancerSeeker.freelancerSeeker.Repository;

import com.freelancerSeeker.freelancerSeeker.Entity.TagsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface TagsRepository extends JpaRepository<TagsEntity,Long> {
    TagsEntity findByTagName(String tag);

    TagsEntity findByTagNameContaining(String tag);
}
