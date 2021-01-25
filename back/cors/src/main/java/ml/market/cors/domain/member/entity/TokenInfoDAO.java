package ml.market.cors.domain;

import ml.market.cors.domain.member.entity.MemberDAO;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tokenInfo")
public class TokenInfoDAO {

    @Id
    @Column(name = "hash")
    private String hash;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberDAO member;

    private LocalDateTime expire_date;
}
