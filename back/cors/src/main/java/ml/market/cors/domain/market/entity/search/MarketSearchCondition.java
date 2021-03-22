package ml.market.cors.domain.market.entity.search;

import lombok.Data;

@Data
public class MarketSearchCondition {
    public enum SortStatus{
        WISH,LATEST
    }
    private Long lastId;
    private String title;
    private SortStatus sort;

}
