package ml.market.cors.domain.chat.entity.dto;

import com.querydsl.core.annotations.QueryProjection;

import java.time.LocalDateTime;

public class MessageDTO {

    private String nickname;
    private String message;
    private LocalDateTime writtenDate;

    @QueryProjection
    public MessageDTO(String nickname, String message, LocalDateTime writtenDate) {
        this.nickname = nickname;
        this.message = message;
        this.writtenDate = writtenDate;
    }
}
