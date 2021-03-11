package ml.market.cors.repository.market;

import ml.market.cors.domain.article.entity.dao.Image_infoDAO;
import ml.market.cors.domain.market.entity.MarketDAO;
import ml.market.cors.domain.market.enums.MarketStatus;
import ml.market.cors.domain.member.entity.MemberDAO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import java.util.List;

public interface MarketRepository extends JpaRepository<MarketDAO,Long> {
    @Query("select m from MarketDAO m where m.member.member_id=:member_id")
    MarketDAO findByMemberId(@Param("member_id") Long member_id);

    List<MarketDAO> findAllByStatus(Pageable pageable, MarketStatus status);

    @Query("select count(marketTb.market_id) > 0 " +
            "from MarketDAO marketTb " +
            "where marketTb.market_id=:marketId and marketTb.member.member_id=:memberId")
    boolean existsByMarketIdAndMemberId(@Param("marketId") long marketId, @Param("memberId") long memberId);


    @Query("select marketTb.member from MarketDAO marketTb where marketTb.market_id=:marketId")
    MemberDAO findByMember(@Param("marketId") Long marketId);

    @Query("select marketTb from MarketDAO marketTb where marketTb.member.member_id=:memberId")
    Page<MarketDAO> findAllByMember(@Param("memberId") Long memberId, Pageable pageable);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    boolean existsByMember(MemberDAO memberId);
}