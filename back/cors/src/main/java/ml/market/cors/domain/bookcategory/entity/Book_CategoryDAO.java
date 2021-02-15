package ml.market.cors.domain.bookcategory.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ml.market.cors.domain.article.entity.dao.ArticleDAO;
import ml.market.cors.domain.article.service.ArticleForm;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="book_category")
@Getter
@NoArgsConstructor
public class Book_CategoryDAO {
    @Id
    @Column(name = "cid")
    private Long cid;

    public Book_CategoryDAO(Long cid) {
        this.cid = cid;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "category")
    private List<ArticleDAO> articles = new ArrayList<>();

    @Column(name = "one_depth")
    private String oneDepth;

    @Column(name = "two_depth")
    private String twoDepth;

    @Column(name = "three_depth")
    private String threeDepth;

    @Column(name = "four_depth")
    private String fourDepth;

    @Column(name = "five_depth")
    private String fiveDepth;

    public Book_CategoryDAO(Long cid, String oneDepth, String twoDepth, String threeDepth, String fourDepth, String fiveDepth) {
        this.cid = cid;
        this.oneDepth = oneDepth;
        this.twoDepth = twoDepth;
        this.threeDepth = threeDepth;
        this.fourDepth = fourDepth;
        this.fiveDepth = fiveDepth;
    }

    public static Book_CategoryDAO createBookCategory(ArticleForm articleForm){
        String[] split = articleForm.getCategory().split(">");
        Book_CategoryDAO book_categoryDAO= null;
        try {
            book_categoryDAO = new Book_CategoryDAO(
                    articleForm.getCid(),
                    split[0],
                    split[1],
                    split[2],
                    split[3],
                    split[4]
            );
        }catch (IndexOutOfBoundsException e){
            return book_categoryDAO;
        }
        return book_categoryDAO;
    }
}
