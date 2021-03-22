package ml.market.cors.domain.board.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReportForm {

    private String title;
    private String content;
}
