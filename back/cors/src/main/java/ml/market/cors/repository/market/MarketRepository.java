package ml.market.cors.repository.market;

import ml.market.cors.domain.market.entity.MarketDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MarketRepository extends JpaRepository<MarketDAO,Long> {

    @Query("select m from MarketDAO m where m.member.member_id=:member_id")
    MarketDAO findByMemberId(@Param("member_id") Long member_id);

}
