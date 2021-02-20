package ml.market.cors.domain.article.entity.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ml.market.cors.domain.member.entity.MemberDAO;

import javax.persistence.*;

@Entity
@Table(name = "wish_list")
@Getter
@NoArgsConstructor
public class Wish_listDAO {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wish_id")
    private Long wish_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberDAO member;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private ArticleDAO article;

    public Wish_listDAO(MemberDAO member, ArticleDAO article) {
        this.member = member;
        this.article = article;
    }
}
