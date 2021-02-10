package ml.market.cors.domain.market.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ml.market.cors.domain.market.enums.MarketStatus;

@AllArgsConstructor
@Getter
public class MarketApproveListVO {
    private long id;
    private String name;
    private MarketStatus status;
}
