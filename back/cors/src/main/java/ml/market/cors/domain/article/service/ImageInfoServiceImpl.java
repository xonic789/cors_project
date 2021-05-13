package ml.market.cors.domain.article.service;


import lombok.RequiredArgsConstructor;
import ml.market.cors.domain.article.entity.dao.ArticleDAO;
import ml.market.cors.domain.article.entity.dao.Image_infoDAO;
import ml.market.cors.domain.article.entity.enums.Division;
import ml.market.cors.repository.article.Image_info_Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ImageInfoServiceImpl implements ImageInfoService {

    private final Image_info_Repository image_info_repository;

    @Override
    @Transactional(readOnly = false)
    public void updateImage(ArticleDAO articleDAO, String... images) {
        Image_infoDAO image_infoDAO = image_info_repository.findById(articleDAO.getImageInfo().getIndex_id()).get();
        image_infoDAO.update_Image_info(images[0],images[1],articleDAO.getDivision());
    }

}
