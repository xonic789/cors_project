package ml.market.cors.domain.chat.entity.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ChatMessage {
    public enum MessageType{
        ENTER,TALK,EXIT
    }
    private MessageType type;
    private String joinId;
    private String nickname;
    private String content;
}
