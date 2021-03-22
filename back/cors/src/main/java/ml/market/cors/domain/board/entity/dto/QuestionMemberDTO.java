package ml.market.cors.domain.board.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;
@Data
@AllArgsConstructor
public class QuestionMemberDTO {
    private int totalPage;
    private List<QuestionBoardDTO> questionBoardList;
}
