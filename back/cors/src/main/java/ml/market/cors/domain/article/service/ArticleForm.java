package ml.market.cors.domain.article.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ml.market.cors.domain.article.entity.enums.Division;
import ml.market.cors.domain.article.entity.enums.Progress;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleForm {

    private Long articleId;
    private Long memberId;
    private String content;
    private String title;
    private String image;
    private Long cid;
    private MultipartFile[] file;
    private int rprice;
    private int tprice;
    private Division division;


}
