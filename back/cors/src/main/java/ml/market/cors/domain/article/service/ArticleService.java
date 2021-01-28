package ml.market.cors.domain.article.service;

import ml.market.cors.domain.article.entity.dao.ArticleDAO;
import ml.market.cors.domain.article.entity.dto.ArticleDTO;
import ml.market.cors.domain.article.entity.enums.Division;
import ml.market.cors.domain.article.entity.enums.Progress;
import ml.market.cors.domain.member.entity.MemberDAO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArticleService {

    ArticleDAO saveArticle(ArticleForm articleForm, MemberDAO memberDAO);
    ArticleDAO findById(Long id);
    ArticleDAO updateArticle(Long article_id,ArticleForm articleForm);
    Progress updateArticleProgress(Long article_id, String progress);

    List<ArticleDTO> findByDivision(Division division, Pageable pageable);
}

