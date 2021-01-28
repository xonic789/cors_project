package ml.market.cors.domain.mail.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;


@Getter
@Data
public class MailVO {
    private String email;
    private int code;
}
