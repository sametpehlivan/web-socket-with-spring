package com.sametp.wsocket.services;

import com.sametp.wsocket.models.ChatRoom;
import com.sametp.wsocket.repositories.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository repository;

    public Optional<String> findByChatId(
            String senderId,
            String recipientId,
            boolean createNewRoomIfNotExists
    ) {
        var queryResult = repository.findByChatIdIn(cratePossibleIds(senderId,recipientId));
        if (queryResult.isEmpty()){
            if (!createNewRoomIfNotExists) return Optional.empty();
            else return Optional.of(
                    create(senderId,recipientId)
            );
        }
        return Optional.of(queryResult.get().getChatId());
    }
    public String create(String senderId, String recipientId){
        var chatId = String.format("%s_%s",senderId,recipientId);
        return repository.save(
                ChatRoom.builder()
                        .chatId(chatId)
                        .senderId(senderId)
                        .recipientId(recipientId)
                        .build()
        ).getChatId();
    }
    private String createChatId(String user1, String user2){
        return  String.format("%s_%s",user1,user2);
    }
    private List<String> cratePossibleIds(String senderId,String recipientId){
        return List.of(
                createChatId(senderId,recipientId),
                createChatId(recipientId,senderId)
        );

    }
}
