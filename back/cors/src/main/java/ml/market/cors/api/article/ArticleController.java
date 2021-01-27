package ml.market.cors.api.article;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import ml.market.cors.domain.article.entity.dto.ArticleDTO;
import ml.market.cors.domain.article.entity.enums.Division;
import ml.market.cors.domain.article.service.ArticleService;
import ml.market.cors.domain.util.Message;
import ml.market.cors.domain.util.StatusEnum;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.Charset;
import java.util.List;

@RestController
@Slf4j
public class ArticleController {

    private final String SALES="sales";
    private final String PURCHASE="purchase";

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    //CRUD


    /**
     * 게시물 분류 (purchase or sales)
     * @param division
     * @param pageable
     * @return
     */
    @GetMapping("/api/articles/{division}")
    public ResponseEntity<Message<Object>> getArticleList(
            @PathVariable String division,
            Pageable pageable){
        if(division.equals(SALES)){
            return success(new ArticleList<>(articleService.findByDivision(Division.SALES, pageable)));
        }else if(division.equals(PURCHASE)){
            return success(new ArticleList<>(articleService.findByDivision(Division.PURCHASE, pageable)));
        }

        return null;
    }

    @Data
    @AllArgsConstructor
    static class ArticleList<T>{
        T data;

    }





    public ResponseEntity<Message<Object>> success(Object data){
        Message<Object> message = new Message<>();
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        message.setStatus(StatusEnum.OK);
        message.setMessage("성공 코드");
        message.setData(data);
        return new ResponseEntity<Message<Object>>(message,headers,HttpStatus.OK);
    }

    public ResponseEntity<Message<Object>> failed(Object data){
        Message<Object> message = new Message<>();
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        message.setStatus(StatusEnum.OK);
        message.setMessage("성공 코드");
        message.setData(data);
        return new ResponseEntity<Message<Object>>(message,headers,HttpStatus.OK);
    }






}
