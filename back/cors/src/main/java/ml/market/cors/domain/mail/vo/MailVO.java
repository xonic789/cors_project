package ml.market.cors.domain.mail.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
public class MailVO {
    public String email;
    public int code;

    public MailVO(@JsonProperty("email") String email, @JsonProperty("code") int code) {
        this.email = email;
        this.code = code;
    }
}
