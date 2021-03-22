package ml.market.cors.repository.member;

import ml.market.cors.domain.member.entity.TokenInfoDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenInfoRepository extends JpaRepository<TokenInfoDAO, Long> {
    public TokenInfoDAO findByTokenindex(long tokenIndex);
}
