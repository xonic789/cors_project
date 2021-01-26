package ml.market.cors.domain.article.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ml.market.cors.domain.bookcategory.entity.Book_CategoryDAO;
import ml.market.cors.domain.member.entity.MemberDAO;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name="article")
@Getter
@NoArgsConstructor
@ToString
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
    private int rprice;

    @Column(name = "write_date")
    private LocalDateTime write_date;

    @Enumerated(EnumType.STRING)
    @Column(name = "progress")
    private Progress progress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="cid")
    private Book_CategoryDAO category;

    @Column(name = "tprice")
    private int tprice;

    @Enumerated(EnumType.STRING)
    private Division division;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberDAO member;

    public ArticleDAO(CountDAO countDAO, String content, int rprice, LocalDateTime write_date,
                      Progress progress, Book_CategoryDAO category, int tprice, Division division, MemberDAO member) {
        this.countDAO = countDAO;
        this.content = content;
        this.rprice = rprice;
        this.write_date = write_date;
        this.progress = progress;
        this.category = category;
        this.tprice = tprice;
        this.division = division;
        this.member = member;
    }
    public ArticleDAO(CountDAO countDAO, String content, int rprice, LocalDateTime write_date,
                      Book_CategoryDAO category, int tprice, Division division, MemberDAO member) {
        this.countDAO = countDAO;
        this.content = content;
        this.rprice = rprice;
        this.write_date = write_date;
        this.category = category;
        this.tprice = tprice;
        this.division = division;
        this.member = member;
    }

    public static ArticleDAO createArticle (MemberDAO memberDAO, String content,
                              int rprice, LocalDateTime write_date,
                              Book_CategoryDAO category, int tprice, Division division){

        return new ArticleDAO(new CountDAO(),content,rprice,write_date,Progress.POSTING,category,tprice,division,memberDAO);
    }

    public void updateArticle(String content, int rprice, LocalDateTime write_date,
                              Progress progress,int tprice, Division division) {
        this.content = content;
        this.rprice = rprice;
        this.write_date = write_date;
        this.progress = progress;
        this.tprice = tprice;
        this.division = division;
    }

    public void updateProgress(Progress progress){
        this.progress=progress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArticleDAO that = (ArticleDAO) o;
        return getRprice() == that.getRprice() && getTprice() == that.getTprice() && Objects.equals(getArticle_id(), that.getArticle_id()) && Objects.equals(getContent(), that.getContent()) && getProgress() == that.getProgress() && getDivision() == that.getDivision();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getArticle_id(), getContent(), getRprice(), getProgress(), getTprice(), getDivision());
    }
}
