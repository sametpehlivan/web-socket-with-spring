package com.sametp.wsocket.services;

import com.sametp.wsocket.models.ChatMessage;
import com.sametp.wsocket.repositories.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final ChatMessageRepository repository;
    private final ChatRoomService roomService;
    public ChatMessage save(ChatMessage chatMessage){
        var chatId = roomService.findByChatId(chatMessage.getSenderId(), chatMessage.getRecipientId(),true )
                .orElseThrow(()->new RuntimeException("Chat Not Found."));
        chatMessage.setId(chatId);
        return repository.save(chatMessage);
    }
    public List<ChatMessage> findChatMessages(
            String senderId,
            String recipientId
    ){
        var chatId = roomService.findByChatId(senderId,recipientId,false)
                .orElse(Strings.EMPTY);
        if (chatId.isEmpty()) return List.of();
        return repository.findByChatId(chatId);
    }

}
