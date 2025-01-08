package com.sametp.wsocket.repositories;

import com.sametp.wsocket.models.ChatRoom;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;
import java.util.Optional;

public interface ChatRoomRepository extends MongoRepository<ChatRoom,String> {
    Optional<ChatRoom> findBySenderIdAndRecipientId(String senderId, String recipientId);

    Optional<ChatRoom> findByChatIdIn(Collection<String> chatIds);
}
