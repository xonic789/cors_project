package ml.market.cors.domain.util;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class UrlConstant {
    private final String MYPAGE = "/api/mypage";
    private final String PROFILE_CHANGE = "/api/change/profile";
    private final String WISH = "/api/wish";
    private final String MARKET = "/api/market";
    private final String LOGIN = "/api/login";
    private final String LOGOUT = "/api/logout";
    private final String JOIN = "/api/join";
    private final String EMAIL_CHECK = "api/check/email";
    private final String EMAIL_CHECK_CODE = "/api/check/code";
    private final String NICKNAME_CHECK = "/api/check/nickname";
    private final String ARTICLE_COOR_SORT_PURCHASELIST = "/api/member/articles/purchase";
    private final String ARTICLE_COOR_SORT_SALESLIST = "/api/member/articles/sales";
    private final String ARTICLE_PURCHASELIST = "/api/articles/purchase";
    private final String ARTICLE_SALESLIST = "/api/articles/sales";
    private final String ARTICLE_ADD = "/api/article";
    private final String ARTICLE = "/api/article";
    private final String MARKET_ARTICLE_ADD = "/api/market/article";
    private final String MARKETLIST = "/api/markets";
    private final String MEMBER_COOR_SORT_MARKETLIST = "/api/member/markets";
    private final String MYPURCHASELIST =  "/api/mypage/purchase";
    private final String MYSALESLIST = "/api/mypage/sales";
    private final String MYMARKETLIST = "/api/mypage/markets";
    private final String WISH_COUNT = "/api/wish/count";
    private final String WISH_SAVE = "/api/wish/save";
    private final String WISH_DELETE = "/api/wish/delete";
    private final String MYWISHLIST = "/api/mypage/wishs";
    private final String MARKET_ADD_REQ = "/api/mypage/market";
    private final String MYMARKET = "/api/mypage/market";
    private final String MARKET_REQ_LIST = "/api/admin/markets";
    private final String ADMIN_MARKET = "/api/admin/market";
    private final String MYMARKET_DETAIL = "/api/mypage/market/detail";
    private final String NOTICE = "/api/notice";
    private final String NOTICE_DELETE = "/api/notice/delete";
    private final String NOTICE_UPDATE = "/api/notice/update";
    private final String NOTICE_SAVE = "/api/notice/save";
    private final String MYQUESTION_ADD = "/api/mypage/question";
    private final String QUESTION = "/api/question";
    private final String ADMIN_QUESTIONLIST = "/api/question/admin/list";
    private final String QUESTION_DELETE = "/api/question/admin/delete";
    private final String QUESTION_COMMENT_SAVE = "/api/question/admin/comment/save";
    private final String QUESTION_COMMENT_DELETE = "/api/question/admin/comment/delete";
    private final String QUESTION_COMMENT_UPDATE = "/api/question/admin/comment/update";
    private final String QUESTION_VIEW = "/api/question/view";

}
