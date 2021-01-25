package ml.market.cors.domain.member.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="blacklist_token")
@Getter()
@NoArgsConstructor
public class Blacklist_TokenDAO {
    @Id
    @Column(name = "hash")
    private String hash;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberDAO member_id;

    @Column(name = "expire_date")
    private Long expire_date;
}
