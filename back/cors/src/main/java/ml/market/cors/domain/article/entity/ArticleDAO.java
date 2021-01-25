package ml.market.cors.domain.article.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import ml.market.cors.domain.bookcategory.entity.Book_CategoryDAO;
import ml.market.cors.domain.member.entity.MemberDAO;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="article")
@Getter()
@NoArgsConstructor
public class ArticleDAO {
    private MemberDAO member_id;

    @Id
    @Column(name = "article_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long article_id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "count_id")
    private CountDAO countDAO;

    @Column(name = "content")
    private String content;

    @Column(name = "rprice")
    private Integer rprice;

    @Column(name = "write_date")
    private LocalDateTime write_date;

    @Column(name = "progress")
    private String progress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="cid")
    private Book_CategoryDAO cid;

    @Column(name = "tprice")
    private Integer tprice;

    @Enumerated(EnumType.STRING)
    private DivisionEnum division;
}
