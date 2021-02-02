package ml.market.cors.api.article;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import ml.market.cors.domain.article.entity.dao.ArticleDAO;
import ml.market.cors.domain.article.entity.enums.Division;
import ml.market.cors.domain.article.entity.enums.Progress;
import ml.market.cors.domain.article.entity.search.ArticleSearchCondition;
import ml.market.cors.domain.article.service.ArticleForm;
import ml.market.cors.domain.article.service.ArticleService;
import ml.market.cors.domain.article.service.CountService;
import ml.market.cors.domain.article.service.ImageInfoService;
import ml.market.cors.domain.bookcategory.entity.Book_CategoryDAO;
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
import org.springframework.security.access.annotation.Secured;
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
    private final CountService countService;

    public ArticleController(ArticleService articleService,ResponseEntityUtils responseEntityUtils,
                             S3Uploader s3Uploader,MemberRepository memberRepository,
                             ImageInfoService imageInfoService,CountService countService) {
        this.articleService = articleService;
        this.responseEntityUtils= responseEntityUtils;
        this.s3Uploader=s3Uploader;
        this.memberRepository=memberRepository;
        this.imageInfoService=imageInfoService;
        this.countService=countService;
    }

    //CRUD


    /**
     * 게시물 분류 (purchase or sales)
     * @param divisions
     * @param pageable
     * @return HttpStatus.OK
     * @return HttpStatus.BAD_REQUEST
     */
    @GetMapping("/api/articles/{divisions}")
    public ResponseEntity<Message<Object>> getArticleList(
            @PathVariable String divisions,
            Pageable pageable, @ModelAttribute ArticleSearchCondition articleSearchCondition){
        System.out.println(articleSearchCondition.getTitle());

        if(divisions.equals(SALES)){
            return responseEntityUtils.getMessageResponseEntityOK(articleService.findAll(Division.SALES, pageable, articleSearchCondition));
        }else if(divisions.equals(PURCHASE)){
            return responseEntityUtils.getMessageResponseEntityOK(articleService.findAll(Division.PURCHASE, pageable, articleSearchCondition));
        }
        return responseEntityUtils.getMessageResponseEntityBadRequest(
                new Errors("URI",
                        "division",
                        divisions,
                        "sales or purchase 만 입력해야 합니다."));
    }


    /**
     * 게시물 디테일
     * @param article_id
     * @return
     */
    @GetMapping("/api/article/{article_id}")
    public ResponseEntity<Message<Object>> getArticleDetail(
            @PathVariable String article_id){

        Long articleId=null;
        ArticleDAO findArticle=null;
        try{
            articleId=Long.parseLong(article_id);
            findArticle = articleService.findById(articleId);
            countService.updateViewCount(findArticle.getCountDAO());
        }catch (NumberFormatException e){
            return responseEntityUtils.getMessageResponseEntityBadRequest(
                    new Errors(
                            "URI",
                            "article_id",
                            article_id,
                            "number 형식으로만 요청 가능합니다"));
        }catch (NullPointerException e){
            return responseEntityUtils.getMessageResponseEntityBadRequest(
                    new Errors(
                            "URI",
                            "article_id",
                            article_id,
                            article_id + "에 해당하는 글이 없습니다."));
        }




        String[] images = {findArticle.getImage_info().getImage2(),findArticle.getImage_info().getImage3()};

        return responseEntityUtils.getMessageResponseEntityOK(
                new ArticleOne(
                        findArticle.getArticle_id(),
                        findArticle.getMember().getMember_id(),
                        findArticle.getMember().getNickname(),
                        findArticle.getTitle(),
                        findArticle.getContent(),
                        findArticle.getRprice(),
                        findArticle.getTprice(),
                        findArticle.getWrite_date(),
                        findArticle.getProgress(),
                        findArticle.getDivision(),
                        findArticle.getImage_info().getImage1(),
                        images
                )
        );
    }


    /**
     *
     * @param articleForm
     * @param jwtCertificationToken
     * @return HttpStatus.Unauthorized
     */
    @PostMapping("/api/article")
    public ResponseEntity<Message<Object>> insertArticle(
            @ModelAttribute ArticleForm articleForm,
            @AuthenticationPrincipal JwtCertificationToken jwtCertificationToken
    ) {

        MemberDAO findMember = null;
        try{
            findMember=getMemberDAO(jwtCertificationToken);
        }catch (NullPointerException e){
            return responseEntityUtils.getMessageResponseEntityUnauthorized(
                    new Errors(
                            "auth",
                            "member",
                            "member eq null",
                            "로그인 해야 합니다."));
        }

        ArticleDAO articleDAO = articleService.saveArticle(articleForm, findMember);

        Object[] images = fileUploadAndUrls(articleForm, articleDAO);

        imageInfoService.updateImage(articleDAO,(String)images[0],(String)images[1]);

        return responseEntityUtils.getMessageResponseEntityCreated(
                new PostArticle(
                        articleDAO.getArticle_id(),
                        articleDAO.getMember().getMember_id(),
                        articleDAO.getTitle(),
                        articleDAO.getContent(),
                        articleDAO.getRprice(),
                        articleDAO.getTprice(),
                        articleDAO.getWrite_date(),
                        articleDAO.getProgress(),
                        articleDAO.getDivision(),
                        articleDAO.getCategory()
                )
        );
    }


    /**
     *
     * @param jwtCertificationToken
     * @param articleForm
     * @return
     */
    @Secured("ROLE_CEO")
    @PostMapping("/api/market/article")
    public ResponseEntity<Message<Object>> insertMarketArticle(
            @AuthenticationPrincipal JwtCertificationToken jwtCertificationToken,
            @ModelAttribute ArticleForm articleForm
    ) {
        MemberDAO findMember = null;
        try{
            findMember=getMemberDAO(jwtCertificationToken);
            Long MemberId = getMarketId(findMember);
        }catch (NullPointerException e){
            return responseEntityUtils.getMessageResponseEntityUnauthorized(
                    new Errors(
                            "auth",
                            "member",
                            "member eq null",
                            "로그인 해야 합니다."));
        }


        ArticleDAO articleDAO = articleService.saveMarketArticle(articleForm, findMember);

        Object[] images = fileUploadAndUrls(articleForm, articleDAO);

        imageInfoService.updateImage(articleDAO, (String) images[0], (String) images[1]);

        return responseEntityUtils.getMessageResponseEntityCreated(
                new PostArticle(
                        articleDAO.getArticle_id(),
                        articleDAO.getMember().getMember_id(),
                        articleDAO.getTitle(),
                        articleDAO.getContent(),
                        articleDAO.getRprice(),
                        articleDAO.getTprice(),
                        articleDAO.getWrite_date(),
                        articleDAO.getProgress(),
                        articleDAO.getDivision(),
                        articleDAO.getMarket().getMarket_id(),
                        articleDAO.getCategory()
                )
        );
    }

    private Long getMarketId(MemberDAO findMember) {
        return findMember.getMember_id();
    }


    /**
     * 게시물 수정 및 파일 업로드 수정
     * @param article_id
     * @param articleForm
     * @return
     */
    @PutMapping("/api/article/{article_id}")
    public ResponseEntity<Message<Object>> updateArticle(
            @PathVariable String article_id,@ModelAttribute ArticleForm articleForm,
            @AuthenticationPrincipal JwtCertificationToken jwtCertificationToken
    ){
        Long articleId=null;
        MemberDAO findMember = null;

        try{
            articleId=Long.parseLong(article_id);
            findMember=getMemberDAO(jwtCertificationToken);
        }catch (NumberFormatException e){
            return responseEntityUtils.getMessageResponseEntityBadRequest(
                    new Errors(
                            "URI",
                            "article_id",
                            article_id,
                            "number 형식으로만 요청 가능합니다"));
        }catch (NullPointerException e){
            return responseEntityUtils.getMessageResponseEntityUnauthorized(
                    new Errors(
                            "auth",
                            "member",
                            "member eq null",
                            "로그인 해야 합니다."));
        }
        ArticleDAO findArticle = articleService.findById(articleId);


        if(findArticle!=null&&eqMemberId(findMember,findArticle)){
            Object[] images = fileUploadAndUrls(articleForm, findArticle);
            ArticleDAO articleDAO = articleService.updateArticle(articleId, articleForm);
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
                            findArticle.getDivision()));
        }else if (findArticle!=null){
            return responseEntityUtils.getMessageResponseEntityForbidden(
                    new Errors(
                            "request",
                            "request user",
                            Long.valueOf(findMember.getMember_id()).toString() + " ne " + findArticle.getMember().getMember_id(),
                            "요청한 유저의 정보가 맞지 않습니다."));
        }
        return responseEntityUtils.getMessageResponseEntityBadRequest(
                new Errors(
                        "URI",
                        "article_id",
                        article_id,
                        article_id + "에 해당하는 글이 없습니다."));
    }



    @DeleteMapping("/api/article/{article_id}")
    public ResponseEntity<Message<Object>> deleteArticle(
            @PathVariable String article_id,
            @AuthenticationPrincipal JwtCertificationToken jwtCertificationToken){
        Long articleId=null;
        MemberDAO findMember = null;

        try{
            articleId=Long.parseLong(article_id);
            findMember=getMemberDAO(jwtCertificationToken);
        }catch (NumberFormatException e){
            return responseEntityUtils.getMessageResponseEntityBadRequest(
                    new Errors(
                            "URI",
                            "article_id",
                            article_id,
                            "number 형식으로만 요청 가능합니다"));
        }catch (NullPointerException e){
            return responseEntityUtils.getMessageResponseEntityUnauthorized(
                    new Errors(
                            "auth",
                            "member",
                            "member eq null",
                            "로그인 해야 합니다."));
        }

        ArticleDAO findArticle = articleService.findById(articleId);

        if (eqMemberId(findMember,findArticle)) {
            articleService.deleteArticle(articleId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return responseEntityUtils.getMessageResponseEntityForbidden(
                    new Errors(
                            "request",
                            "request user",
                            Long.valueOf(findMember.getMember_id()).toString() + " ne " + findArticle.getMember().getMember_id(),
                            "요청한 유저의 정보가 맞지 않습니다."));
        }
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


    private MemberDAO getMemberDAO(@AuthenticationPrincipal JwtCertificationToken jwtCertificationToken) throws NullPointerException{
        String email = jwtCertificationToken.getName();
        return memberRepository.findByEmail(email);
    }

    private boolean eqMemberId(MemberDAO member,ArticleDAO article){
        if(article.getMember().getMember_id()== member.getMember_id()){
            return true;
        }
        return false;
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
    }

    @Data
    @AllArgsConstructor
    static class PostArticle{
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
        private Book_CategoryDAO book_categoryDAO;

        public PostArticle(Long articleId, Long memberId, String title,
                           String content, int rprice, int tprice, LocalDateTime writeDate,
                           Progress progress, Division division, Book_CategoryDAO book_categoryDAO) {
            this.articleId = articleId;
            this.memberId = memberId;
            this.title = title;
            this.content = content;
            this.rprice = rprice;
            this.tprice = tprice;
            this.writeDate = writeDate;
            this.progress = progress;
            this.division = division;
            this.book_categoryDAO = book_categoryDAO;
        }
    }
    @Data
    @AllArgsConstructor
    static class ArticleOne{
        private Long articleId;
        private Long memberId;
        private String nickname;
        private String title;
        private String content;
        private int rprice;
        private int tprice;
        private LocalDateTime writeDate;
        private Progress progress;
        private Division division;
        private String thumbnail;
        private String[] image;
    }





}
