package ml.market.cors.domain.comment.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CommentBoardDTO {
    private Long commentId;
    private String content;
    private LocalDateTime writeDate;
    private String nickname;
}
