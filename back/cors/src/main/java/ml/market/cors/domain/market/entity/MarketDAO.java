package ml.market.cors.domain.market.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import ml.market.cors.domain.article.entity.dao.ArticleDAO;
import ml.market.cors.domain.member.entity.MemberDAO;

import javax.persistence.*;

@Entity
@Table(name="market")
@Getter()
@NoArgsConstructor
public class MarketDAO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "market_id")
    private Long market_id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberDAO member;

    @OneToOne(fetch = FetchType.LAZY,mappedBy = "market")
    private ArticleDAO article;

    @Column(name = "name")
    private String name;

    @Column(name = "intro")
    private String intro;

    @Column(name = "location")
    private String location;

    @Column(name = "latitude")
    private Integer latitude;

    @Column(name = "longtitude")
    private Integer longtitude;

    @Column(name = "image")
    private String image;

}
