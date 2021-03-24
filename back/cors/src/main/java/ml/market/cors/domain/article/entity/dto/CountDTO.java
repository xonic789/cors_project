package ml.market.cors.domain.article.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ml.market.cors.domain.article.entity.dao.CountDAO;

@Data
@AllArgsConstructor
public class CountDTO {

    private Long countId;
    private int views;
    private int chatCount;
    private int wishCount;

    public CountDTO(CountDAO countDAO) {
        this.countId = countDAO.getCountId();
        this.views = countDAO.getViews();
        this.chatCount = countDAO.getChatCount();
        this.wishCount = countDAO.getWishCount();
    }
}
