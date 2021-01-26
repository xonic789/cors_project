package ml.market.cors.domain.member.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import ml.market.cors.domain.member.entity.MemberDAO;

import ml.market.cors.domain.member.entity.MemberDAO;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "token_info")
@NoArgsConstructor
@Getter
public class TokenInfoDAO {

    @Id
    @Column(name = "hash")
    private String hash;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberDAO member_id;

    @Column(name = "expire_date")
    private long expire_date;

    public TokenInfoDAO(String hash, MemberDAO member_id, long expireTime) {
        this.expire_date = expireTime;
        this.member_id = member_id;
        this.hash = hash;
    }

    public TokenInfoDAO(String hash) {
        this.hash = hash;
    }
}
