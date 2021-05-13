package ml.market.cors.domain.article.service;

import ml.market.cors.domain.article.entity.dao.CountDAO;

public interface CountService {
    void updateViewCount(CountDAO countDAO);
    void updateChatCount(CountDAO countDAO);
    void updateWishCount(CountDAO countDAO);
}
