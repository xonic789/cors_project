package ml.market.cors.domain.util;

import lombok.Data;

@Data
public class Message<T> {

    private StatusEnum status;
    private String message;
    private T data;

    public Message() {
        this.status=StatusEnum.BAD_REQUEST;
        this.data=null;
        this.message=null;
    }

    public Message(StatusEnum status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
