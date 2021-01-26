package ml.market.cors.repository.member;

import ml.market.cors.domain.member.entity.Blacklist_TokenDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("blacklistTokenRepository")
public interface Blacklist_TokenRepository extends JpaRepository<Blacklist_TokenDAO, String> {
}
