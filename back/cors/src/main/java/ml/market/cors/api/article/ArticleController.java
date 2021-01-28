package ml.market.cors.api.article;


import lombok.extern.slf4j.Slf4j;
import ml.market.cors.domain.article.entity.dto.ArticleDTO;
import ml.market.cors.domain.article.entity.enums.Division;
import ml.market.cors.domain.article.entity.search.ArticleSearch;
import ml.market.cors.domain.article.service.ArticleForm;
import ml.market.cors.domain.article.service.ArticleService;
import ml.market.cors.domain.member.entity.MemberDAO;
import ml.market.cors.domain.security.member.JwtCertificationToken;
import ml.market.cors.domain.util.Errors;
import ml.market.cors.domain.util.Message;
import ml.market.cors.domain.util.ResponseEntityUtils;
import ml.market.cors.domain.util.StatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.Charset;
import java.util.List;

@RestController
@Slf4j
public class ArticleController {

    private final String SALES="sales";
    private final String PURCHASE="purchase";

    private final ArticleService articleService;
    private final ResponseEntityUtils responseEntityUtils;

    public ArticleController(ArticleService articleService,ResponseEntityUtils responseEntityUtils) {
        this.articleService = articleService;
        this.responseEntityUtils= responseEntityUtils;
    }

    //CRUD


    /**
     * 게시물 분류 (purchase or sales)
     * @param division
     * @param pageable
     * @return List.size()==0 HttpStatus.No_CONTENT
     * @return List.size()>0 HttpStatus.OK
     * @return HttpStatus.BAD_REQUEST
     */
    @GetMapping("/api/articles/{division}")
    public ResponseEntity<Message<Object>> getArticleList(
            @PathVariable String division,
            Pageable pageable, @ModelAttribute ArticleSearch articleSearch){
        if(division.equals(SALES)){
            List<ArticleDTO> list = articleService.findByDivision(Division.SALES, pageable);
            if(list.size()==0){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }else{
                return responseEntityUtils.getMessageResponseEntityOK(list);
            }
        }else if(division.equals(PURCHASE)){
            List<ArticleDTO> list = articleService.findByDivision(Division.PURCHASE, pageable);
            if(list.size()==0){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }else {
                return responseEntityUtils.getMessageResponseEntityOK(list);
            }
        }
        return responseEntityUtils.getMessageResponseEntityBadRequest(
                new Errors("URI",
                        "division",
                        division,
                        "sales or purchase 만 입력해야 합니다."));
    }
    @PostMapping("/api/article")
    public ResponseEntity<Message<Object>> insertArticle(
            MultipartFile file
    ){
        return responseEntityUtils.getMessageResponseEntityBadRequest(null);
    }









}
