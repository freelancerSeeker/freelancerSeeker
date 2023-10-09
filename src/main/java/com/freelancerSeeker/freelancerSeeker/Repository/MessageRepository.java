package com.freelancerSeeker.freelancerSeeker.Repository;

import com.freelancerSeeker.freelancerSeeker.Classes.ChatMessage;
import com.freelancerSeeker.freelancerSeeker.Entity.MessageEntity;
import com.freelancerSeeker.freelancerSeeker.Entity.UserSiteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MessageRepository extends JpaRepository<MessageEntity,Long> {
    List<MessageEntity> findBySenderAndReceiver(UserSiteEntity sender, UserSiteEntity receiver);

}
