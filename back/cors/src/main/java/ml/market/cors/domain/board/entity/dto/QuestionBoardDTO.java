package ml.market.cors.domain.board.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class QuestionBoardDTO {
    private long questionId;
    private String title;
    private String content;
    private LocalDateTime writeDate;
    private String email;
}
