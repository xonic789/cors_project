package ml.market.cors.domain.chat.entity.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;


@Data
public class ChattingRoomDTO {

    private String joinId;
    private String nickname;
    private String title;
    private int tprice;
    private Long chattingId;

    @QueryProjection
    public ChattingRoomDTO(String joinId, String nickname, String title, int tprice, Long chattingId) {
        this.joinId = joinId;
        this.nickname = nickname;
        this.title = title;
        this.tprice = tprice;
        this.chattingId = chattingId;
    }

    @QueryProjection
    public ChattingRoomDTO(String joinId, String title, Long chattingId) {
        this.joinId = joinId;
        this.title = title;
        this.chattingId = chattingId;
    }
}
