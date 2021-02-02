package ml.market.cors.domain.article.entity.dao;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="count")
@Getter
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

    public void updateViewCount(int views){
        this.views=views;
    }
    public void updateChatCount(int chat_count){
        this.chat_count=chat_count;
    }
    public void updateWishCount(int wish_count){
        this.wish_count=wish_count;
    }

}
