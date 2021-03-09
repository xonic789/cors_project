package ml.market.cors.domain.article.entity.dao;

import lombok.Getter;
import lombok.NoArgsConstructor;
import ml.market.cors.domain.article.entity.enums.Division;
import ml.market.cors.domain.article.entity.enums.Progress;
import ml.market.cors.domain.article.service.ArticleForm;
import ml.market.cors.domain.bookcategory.entity.Book_CategoryDAO;
import ml.market.cors.domain.market.entity.MarketDAO;
import ml.market.cors.domain.member.entity.MemberDAO;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

@Entity
@Table(name="article")
@Getter
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

    @Column(name = "title")
    private String title;

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


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "index_id")
    private Image_infoDAO imageInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "market_id")
    private MarketDAO market;

    /** by 이승우
     * 일반 게시글용 생성자
     */
    public ArticleDAO(CountDAO countDAO, String content, int rprice, LocalDateTime write_date,
                      Progress progress, int tprice, Division division,
                      Image_infoDAO imageInfo, String title, MemberDAO memberDAO, Book_CategoryDAO category) {
        this.countDAO = countDAO;
        this.content = content;
        this.rprice = rprice;
        this.write_date = write_date;
        this.progress = progress;
        this.tprice = tprice;
        this.division = division;
        this.imageInfo = imageInfo;
        this.title=title;
        this.member=memberDAO;
        this.category=category;
    }

    /** by 이승우
     * 마켓 게시글용 생성자
     */
    public ArticleDAO(CountDAO countDAO, String content, int rprice, LocalDateTime write_date,
                      Progress progress, int tprice, Division division,
                      Image_infoDAO imageInfo, String title, MemberDAO member, Book_CategoryDAO category, MarketDAO market) {
        this.countDAO = countDAO;
        this.content = content;
        this.rprice = rprice;
        this.write_date = write_date;
        this.progress = progress;
        this.tprice = tprice;
        this.division = division;
        this.member = member;
        this.imageInfo = imageInfo;
        this.title=title;
        this.category=category;
        this.market=market;
    }

    public ArticleDAO(Long article_id) {
        this.article_id = article_id;
    }

    /**
     * by 이승우
     * @param articleForm / @ModelAttribute
     * @param member / find Member
     * @param book_categoryDAO / findById Book_CategoryDAO
     * @return create ArticleDAO
     */
    public static ArticleDAO createArticle(ArticleForm articleForm, MemberDAO member, Book_CategoryDAO book_categoryDAO){
        return new ArticleDAO(
                new CountDAO(), articleForm.getContent(),
                articleForm.getRprice(), LocalDateTime.now(ZoneId.of("Asia/Seoul")),
                Progress.POSTING, articleForm.getTprice(),
                articleForm.getDivision(),
                new Image_infoDAO(articleForm.getImage(),articleForm.getDivision()),
                articleForm.getTitle(),member,book_categoryDAO);
    }

    /**
     * by 이승우
     * @param articleForm // @ModelAttribute
     * @param member // find Member
     * @param book_categoryDAO // find book_categoryDAO
     * @param market // find market
     * @return // create Article
     */
    public static ArticleDAO createArticleMarket(ArticleForm articleForm, MemberDAO member,Book_CategoryDAO book_categoryDAO,MarketDAO market){
        return new ArticleDAO(
                new CountDAO(), articleForm.getContent(),
                articleForm.getRprice(), LocalDateTime.now(ZoneId.of("Asia/Seoul")),
                Progress.POSTING, articleForm.getTprice(),
                articleForm.getDivision(),
                new Image_infoDAO(articleForm.getImage(),articleForm.getDivision()),
                articleForm.getTitle(),member,book_categoryDAO,market);
    }

    /**
     * by 이승운
     * @param articleForm
     * @param image_info
     * @param countDAO
     * @return
     */
    public ArticleDAO updateArticle(ArticleForm articleForm,Image_infoDAO image_info,CountDAO countDAO) {
        this.content = articleForm.getContent();
        this.tprice = articleForm.getTprice();
        this.division = articleForm.getDivision();
        this.progress = articleForm.getProgress();
        this.imageInfo =image_info;
        this.countDAO=countDAO;
        return this;
    }

    public Progress updateProgress(Progress progress){
        this.progress=progress;
        return this.getProgress();
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
