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
    private Book_CategoryDAO category;

    @Column(name = "tprice")
    private Integer tprice;

    @Enumerated(EnumType.STRING)
    private DivisionEnum division;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberDAO member;

    public void createArticle(MemberDAO memberDAO,CountDAO countDAO,String content,
                                    int rprice, LocalDateTime write_date,String progress,
                                    Book_CategoryDAO category,int tprice, DivisionEnum division){
        this.member=memberDAO;
        this.countDAO=countDAO;
        this.content=content;
        this.rprice=rprice;
        this.write_date=write_date;
        this.progress=progress;
        this.category=category;
        this.tprice=tprice;
        this.division=division;
    }



}
