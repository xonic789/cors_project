package ml.market.cors.domain.util;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;

@Component
public class ResponseEntityUtils {

    private HttpHeaders headers;
    private Message message;

    public ResponseEntity<Message<Object>> getMessageResponseEntityOK(Object data){
        headers = getJsonHeaders();
        message = getSuccessMessage(data);
        return new ResponseEntity<Message<Object>>(message,headers, HttpStatus.OK);
    }

    public ResponseEntity<Message<Object>> getMessageResponseEntityCreated(Object data){
        headers = getJsonHeaders();
        message = getCreatedMessage(data);
        return new ResponseEntity<Message<Object>>(message,headers, HttpStatus.CREATED);
    }


    public ResponseEntity<Message<Object>> getMessageResponseEntityNotFound(Object data){
        headers = getJsonHeaders();
        message = getNotFoundMessage(data);
        return new ResponseEntity<Message<Object>>(message,headers, HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Message<Object>> getMessageResponseEntityBadRequest(Object data){
        headers = getJsonHeaders();
        message = getBadRequestMessage(data);
        return new ResponseEntity<Message<Object>>(message,headers,HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Message<Object>> getMessageResponseEntityForbidden(Object data){
        headers = getJsonHeaders();
        message = getForbiddenMessage(data);
        return new ResponseEntity<Message<Object>>(message,headers,HttpStatus.FORBIDDEN);
    }
    public ResponseEntity<Message<Object>> getMessageResponseEntityUnauthorized(Object data){
        headers = getJsonHeaders();
        message = getUnauthorizedMessage(data);
        return new ResponseEntity<Message<Object>>(message,headers,HttpStatus.UNAUTHORIZED);
    }



    private Message<Object> getCreatedMessage(Object data) {
        message = new Message<>();
        message.setStatus(StatusEnum.CREATED);
        message.setMessage("생성 성공 코드");
        message.setData(data);
        return message;
    }

    private Message<Object> getSuccessMessage(Object data) {
        message = new Message<>();
        message.setStatus(StatusEnum.OK);
        message.setMessage("성공 코드");
        message.setData(data);
        return message;
    }

    private Message<Object> getBadRequestMessage(Object data) {
        message = new Message<>();
        message.setStatus(StatusEnum.BAD_REQUEST);
        message.setMessage("잘못된 요청입니다.");
        message.setData(data);
        return message;
    }
    private Message<Object> getNotFoundMessage(Object data) {
        message = new Message<>();
        message.setStatus(StatusEnum.NOT_FOUND);
        message.setMessage("페이지를 찾을 수 없습니다.");
        message.setData(data);
        return message;
    }

    private Message<Object> getForbiddenMessage(Object data) {
        message = new Message<>();
        message.setStatus(StatusEnum.FORBIDDEN);
        message.setMessage("권한이 불충분합니다");
        message.setData(data);
        return message;
    }

    private Message<Object> getUnauthorizedMessage(Object data) {
        message = new Message<>();
        message.setStatus(StatusEnum.UN_AUTHORIZED);
        message.setMessage("인증되지 않았습니다");
        message.setData(data);
        return message;
    }

    private HttpHeaders getJsonHeaders() {
        headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        return headers;
    }

}
