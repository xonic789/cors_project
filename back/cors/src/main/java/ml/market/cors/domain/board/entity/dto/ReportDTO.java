package ml.market.cors.domain.board.entity.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ReportDTO {
    private String title;
    private String content;
    private String nickname;
    private LocalDateTime writeDate;

    @QueryProjection
    public ReportDTO(String title, String content, String nickname, LocalDateTime writeDate) {
        this.title = title;
        this.content = content;
        this.nickname = nickname;
        this.writeDate = writeDate;
    }
}
