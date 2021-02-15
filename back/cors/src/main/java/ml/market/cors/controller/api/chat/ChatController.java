package ml.market.cors.controller.api.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ml.market.cors.domain.chat.entity.dto.ChatMessage;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ChatController {

    private final SimpMessageSendingOperations messagingTemplate;

    @MessageMapping("/chat/message")
    public void message(ChatMessage chatMessage){
        log.info("메시지"+chatMessage);
        System.out.println("ChatController.message"+chatMessage);
        messagingTemplate.convertAndSend("api/sub/chat/room/"+chatMessage.getJoinId(),chatMessage);

    }
}