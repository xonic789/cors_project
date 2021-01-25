package ml.market.cors.domain.bookcategory.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import ml.market.cors.domain.article.entity.ArticleDAO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="book_category")
@Getter()
@NoArgsConstructor
public class Book_CategoryDAO {
    @Id
    @Column(name = "cid")
    private Long cid;

    public Book_CategoryDAO(Long cid) {
        this.cid = cid;
    }

    @OneToMany(mappedBy = "category")
    private List<ArticleDAO> articles = new ArrayList<>();

    @Column(name = "one_depth")
    private String one_depth;

    @Column(name = "two_depth")
    private String two_depth;

    @Column(name = "three_depth")
    private String three_depth;

    @Column(name = "four_depth")
    private String four_depth;

    @Column(name = "five_depth")
    private String five_depth;

}
