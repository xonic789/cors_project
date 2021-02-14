package ml.market.cors.controller.api.chat;

import lombok.RequiredArgsConstructor;
import ml.market.cors.domain.chat.entity.dto.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class ChatController {

    private final SimpMessageSendingOperations messagingTemplate;

    @MessageMapping("chat/message")
    public void message(ChatMessage chatMessage){
        messagingTemplate.convertAndSend("api/sub/chat/room/"+chatMessage.getJoinId(),chatMessage);
    }
}