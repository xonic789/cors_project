package ml.market.cors.api.article;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import ml.market.cors.domain.article.entity.dao.ArticleDAO;
import ml.market.cors.domain.article.entity.enums.Division;
import ml.market.cors.domain.article.entity.enums.Progress;
import ml.market.cors.domain.article.entity.search.ArticleSearch;
import ml.market.cors.domain.article.service.ArticleForm;
import ml.market.cors.domain.article.service.ArticleService;
import ml.market.cors.domain.article.service.ImageInfoService;
import ml.market.cors.domain.member.entity.MemberDAO;
import ml.market.cors.domain.security.member.JwtCertificationToken;
import ml.market.cors.domain.util.Errors;
import ml.market.cors.domain.util.Message;
import ml.market.cors.domain.util.ResponseEntityUtils;
import ml.market.cors.repository.member.MemberRepository;
import ml.market.cors.upload.S3Uploader;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;

@RestController
@Slf4j
public class ArticleController {

    private final String SALES="sales";
    private final String PURCHASE="purchase";

    private final ArticleService articleService;
    private final S3Uploader s3Uploader;
    private final ResponseEntityUtils responseEntityUtils;
    private final MemberRepository memberRepository;
    private final ImageInfoService imageInfoService;


    public ArticleController(ArticleService articleService,ResponseEntityUtils responseEntityUtils,
                             S3Uploader s3Uploader,MemberRepository memberRepository,ImageInfoService imageInfoService) {
        this.articleService = articleService;
        this.responseEntityUtils= responseEntityUtils;
        this.s3Uploader=s3Uploader;
        this.memberRepository=memberRepository;
        this.imageInfoService=imageInfoService;
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
            return responseEntityUtils.getMessageResponseEntityOK(articleService.findAll(Division.SALES, pageable,articleSearch));
        }else if(division.equals(PURCHASE)){
            return responseEntityUtils.getMessageResponseEntityOK(articleService.findAll(Division.PURCHASE, pageable,articleSearch));
        }
        return responseEntityUtils.getMessageResponseEntityBadRequest(
                new Errors("URI",
                        "division",
                        division,
                        "sales or purchase 만 입력해야 합니다."));
    }




    @GetMapping("/api/article/{article_id}")
    public ResponseEntity<Message<Object>> getArticleDetail(
            @PathVariable String article_id){
        try{
            return responseEntityUtils.getMessageResponseEntityOK(articleService.findById(Long.parseLong(article_id)));
        }catch (NumberFormatException e){
            return responseEntityUtils.getMessageResponseEntityBadRequest(
                    new Errors(
                            "URI",
                            "article_id",
                            article_id.toString(),
                            "number 형식으로만 요청 가능합니다"));
        }


    }

    @Data
    @AllArgsConstructor
    static class ArticleOne{
//        private
    }


    /**
     * 게시물 등록 및 파일 업로드
     * @param articleForm
     * @param jwtCertificationToken
     * @return
     */
    @PostMapping("/api/article")
    public ResponseEntity<Message<Object>> insertArticle(
            @ModelAttribute ArticleForm articleForm,
            @AuthenticationPrincipal JwtCertificationToken jwtCertificationToken
    ) {
        String name = jwtCertificationToken.getName();
        MemberDAO findMember = memberRepository.findByEmail(name);

        ArticleDAO articleDAO = articleService.saveArticle(articleForm, findMember);

        Object[] images = fileUploadAndUrls(articleForm, articleDAO);

        imageInfoService.updateImage(articleDAO,(String)images[0],(String)images[1]);

        return responseEntityUtils.getMessageResponseEntityCreated(articleDAO);
    }

    private Object[] fileUploadAndUrls(ArticleForm articleForm, ArticleDAO articleDAO) {
        MultipartFile[] files = articleForm.getFile();

        Object[] images = Arrays.stream(files).map(file -> {
            try {
                return s3Uploader.upload(file, "static", articleDAO.getArticle_id(),"article");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }).toArray();
        return images;
    }

    /**
     * 게시물 수정 및 파일 업로드 수정
     * @param article_id
     * @param articleForm
     * @return
     */
    @PutMapping("/api/article/{article_id}")
    public ResponseEntity<Message<Object>> updateArticle(
            @PathVariable Long article_id,@ModelAttribute ArticleForm articleForm){
        ArticleDAO findArticle = articleService.findById(article_id);
        Object[] images = fileUploadAndUrls(articleForm, findArticle);
        ArticleDAO articleDAO = articleService.updateArticle(article_id, articleForm);
        imageInfoService.updateImage(findArticle,(String)images[0],(String)images[1]);

        return responseEntityUtils.getMessageResponseEntityCreated(
                new PutArticle(
                        findArticle.getArticle_id(),
                        findArticle.getMember().getMember_id(),
                        findArticle.getTitle(),
                        findArticle.getContent(),
                        findArticle.getRprice(),
                        findArticle.getTprice(),
                        findArticle.getWrite_date(),
                        findArticle.getProgress(),
                        findArticle.getDivision(),
                        findArticle.getMarket().getMarket_id()));
    }

    @Data
    @AllArgsConstructor
    static class PutArticle{
        private Long articleId;
        private Long memberId;
        private String title;
        private String content;
        private int rprice;
        private int tprice;
        private LocalDateTime writeDate;
        private Progress progress;
        private Division division;
        private Long marketId;
    }

    @DeleteMapping("/api/article/{article_id}")
    public ResponseEntity<Message<Object>> deleteArticle(@PathVariable Long article_id){
        articleService.deleteArticle(article_id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}
