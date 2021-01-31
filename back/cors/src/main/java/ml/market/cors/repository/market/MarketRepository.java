package ml.market.cors.repository.market;

import ml.market.cors.domain.market.entity.MarketDAO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarketRepository extends JpaRepository<MarketDAO,Long> {

}
