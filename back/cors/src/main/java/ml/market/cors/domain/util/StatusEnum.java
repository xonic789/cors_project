package ml.market.cors.domain.util;

public enum StatusEnum {

    OK(200, "OK"), // 성공
    CREATED(201,"CREATED"), // POST insert 성공
    NO_CONTENT(204,"NO_CONTENT"), // 데이터가 없음
    BAD_REQUEST(400, "BAD_REQUEST"), // 잘못된 요청
    UN_AUTHORIZED(401,"UN_AUTHORIZED"), // 비인증 상태 (로그인이 되지 않은 상태)
    FORBIDDEN(403,"FORBIDDEN"), //권한 불충분 (로그인이 됐으나 권한이 불충분한 상태)
    NOT_FOUND(404, "NOT_FOUND"), // 페이지 찾을 수 없음
    INTERNAL_SERER_ERROR(500, "INTERNAL_SERVER_ERROR"); // 서버 에러

    int statusCode;

    String code;

    StatusEnum(int statusCode, String code) {
        this.statusCode = statusCode;
        this.code = code;
    }

}
