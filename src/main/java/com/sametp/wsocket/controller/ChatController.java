package com.sametp.wsocket.controller;

import com.sametp.wsocket.models.ChatMessage;
import com.sametp.wsocket.models.ChatNotification;
import com.sametp.wsocket.services.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageService chatMessageService;
    @MessageMapping
    public void processMessage(
            @Payload ChatMessage chatMessage
    ){
        ChatMessage savedMessage = chatMessageService.save(chatMessage);
        messagingTemplate.convertAndSendToUser(
                savedMessage.getRecipientId(), "/queue/messages",
                ChatNotification.builder()
                        .id(savedMessage.getId())
                        .senderId(savedMessage.getSenderId())
                        .recipientId(savedMessage.getRecipientId())
                        .content(savedMessage.getContent())
                        .build()
        );
    }
    @GetMapping("/messages/{senderId}/{recipientId}")
    @ResponseBody
    public List<ChatMessage> findByChatMessages(
            @Payload("senderId") String senderId,
            @Payload("recipientId") String recipientId
    ){
        return chatMessageService.findChatMessages(senderId,recipientId);
    }
}
