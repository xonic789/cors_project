package ml.market.cors.domain.article.service;

import lombok.RequiredArgsConstructor;
import ml.market.cors.domain.article.entity.dao.CountDAO;
import ml.market.cors.repository.article.CountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CountServiceImpl implements CountService{

    private final CountRepository countRepository;

    @Override
    @Transactional(readOnly = false)
    public void updateViewCount(CountDAO countDAO) {
        CountDAO count = countRepository.findById(countDAO.getCount_id()).get();
        count.updateViewCount(count.getViews()+1);
    }

    @Override
    @Transactional(readOnly = false)
    public void updateChatCount(CountDAO countDAO) {
        CountDAO count = countRepository.findById(countDAO.getCount_id()).get();
        count.updateChatCount(count.getChat_count()+1);
    }

    @Override
    @Transactional(readOnly = false)
    public void updateWishCount(CountDAO countDAO) {
        CountDAO count = countRepository.findById(countDAO.getCount_id()).get();
        count.updateWishCount(count.getWish_count()+1);
    }
}
