package ml.market.cors.domain.board.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ml.market.cors.domain.comment.entity.dto.CommentBoardDTO;
import java.util.List;

@Data
@AllArgsConstructor
public class QuestionViewDTO {
    private int totalPage;
    private long questionId;
    private String content;
    private String title;
    List<CommentBoardDTO> comment;
}
