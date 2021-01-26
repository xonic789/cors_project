package ml.market.cors.repository.member;

import ml.market.cors.domain.member.entity.MemberDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("memberRepository")
public interface MemberRepository extends JpaRepository<MemberDAO, Long> {
    public MemberDAO findByEmail(String email);
}
