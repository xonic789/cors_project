package ml.market.cors.domain.article.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import ml.market.cors.domain.article.entity.ArticleDAO;

import javax.persistence.*;

@Entity
@Table(name="count")
@Getter()
@NoArgsConstructor
public class CountDAO {
    @Column(name = "chat_count")
    private int chat_count;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "count_id")
    private Long count_id;


    @Column(name = "views")
    private int views;

    @Column(name = "wish_count")
    private int wish_count;

}
