package ml.market.cors.domain.market.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import ml.market.cors.domain.article.entity.dao.ArticleDAO;
import ml.market.cors.domain.market.enums.MarketStatus;
import ml.market.cors.domain.member.entity.MemberDAO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="market")
@Getter()
@NoArgsConstructor
@AllArgsConstructor
public class MarketDAO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "market_id")
    private Long market_id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberDAO member;

    @OneToMany(mappedBy = "market",cascade = CascadeType.ALL)
    private List<ArticleDAO> articles = new ArrayList<>();

    @Column(name = "name")
    private String name;

    @Column(name = "intro")
    private String intro;

    @Column(name = "location")
    private String location;

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "longitude")
    private double longitude;

    @Column(name = "image")
    private String image;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private MarketStatus status;

    @Column(name = "canclecause")
    private String canclecause;

    public MarketDAO(MemberDAO member, String name, String intro, String location, double latitude, double longitude, String image, MarketStatus status, String canclecause) {
        this.member = member;
        this.name = name;
        this.intro = intro;
        this.canclecause = canclecause;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.image = image;
        this.status = status;
    }
}
