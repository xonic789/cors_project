package ml.market.cors.repository.member;

import ml.market.cors.domain.member.entity.MemberDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<MemberDAO, Long> {
    MemberDAO findByEmail(String email);

    MemberDAO findById(long memberId);

    @Query("select memberTb from MemberDAO memberTb where memberTb.member_id=:member_id")
    List<MemberDAO> findByMemberId(@Param("member_id")long memberId);

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);

    @Query("select COUNT(memberTb.member_id) > 0 from MemberDAO memberTb where memberTb.member_id=:member_id and memberTb.nickname=:nickname")
    boolean existsByMemberIdAndNickname(@Param("member_id") long memberId, @Param("nickname") String nickname);
}
