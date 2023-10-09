package com.freelancerSeeker.freelancerSeeker.Repository;

import com.freelancerSeeker.freelancerSeeker.Classes.ChatMessage;
import com.freelancerSeeker.freelancerSeeker.Entity.MessageEntity;
import com.freelancerSeeker.freelancerSeeker.Entity.UserSiteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageRepository extends JpaRepository<MessageEntity,Long> {
    List<MessageEntity> findBySenderAndReceiver(UserSiteEntity sender, UserSiteEntity receiver);
    List<MessageEntity> findBySender_IdAndReceiver_Id(Long senderId, Long receiverId);
    @Query("SELECT m FROM MessageEntity m WHERE (m.sender.id = :senderId AND m.receiver.id = :receiverId) OR (m.sender.id = :receiverId AND m.receiver.id = :senderId)")
    List<MessageEntity> findMessagesBetweenUsers(Long senderId, Long receiverId);
}

