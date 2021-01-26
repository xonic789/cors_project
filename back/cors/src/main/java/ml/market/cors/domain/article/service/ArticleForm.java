package ml.market.cors.domain.article.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ml.market.cors.domain.article.entity.CountDAO;
import ml.market.cors.domain.article.entity.Division;
import ml.market.cors.domain.article.entity.Progress;
import ml.market.cors.domain.bookcategory.entity.Book_CategoryDAO;
import ml.market.cors.domain.member.entity.MemberDAO;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleForm {

    private Long articleId;

    private String content;

    private int rprice;

    private LocalDateTime writeDate;

    private Progress progress;

    private int tprice;

    private Division division;
}
