package ml.market.cors.domain.util;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Errors {

    private String location;
    private String param;
    private String value;
    private String msg;

    public Errors(String location, String param, String value, String msg) {
        this.location = location;
        this.param = param;
        this.value = value;
        this.msg = msg;
    }

}
