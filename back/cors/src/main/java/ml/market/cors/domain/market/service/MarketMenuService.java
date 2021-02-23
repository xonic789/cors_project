package ml.market.cors.domain.market.service;

import ml.market.cors.domain.market.entity.dto.MarketArticleDTO;
import ml.market.cors.domain.market.entity.dto.MarketDTO;
import ml.market.cors.domain.market.entity.search.MarketSearchCondition;
import ml.market.cors.domain.security.member.JwtCertificationToken;

import java.util.List;

public interface MarketMenuService {

    List<MarketDTO> findAll(MarketSearchCondition marketSearchCondition);
    List<MarketArticleDTO> findArticlesByMarketId(Long marketId);
    List<MarketDTO> findAllByMemberLocation(JwtCertificationToken jwtCertificationToken,MarketSearchCondition marketSearchCondition) throws IllegalStateException;
}
