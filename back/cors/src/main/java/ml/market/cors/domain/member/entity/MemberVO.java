package ml.market.cors.domain.member.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

@Data
public class MemberVO {
    private String id;

    private String passwd;

    private String email;

    private String role;

    private String address;

    private String nickname;

    public MemberVO(@JsonProperty("id") String id, @JsonProperty("passwd") String passwd
            , @JsonProperty("email") String email, @JsonProperty("address") String address
    ,@JsonProperty("nickname") String nickname) {
        this.id = id;
        this.passwd = passwd;
        this.email = email;
        this.address = address;
        this.nickname = nickname;
    }

    public MemberVO(@JsonProperty("passwd") String passwd, @JsonProperty("email") String email
            , @JsonProperty("address") String address, @JsonProperty("nickname") String nickname) {
        this.passwd = passwd;
        this.email = email;
        this.address = address;
        this.nickname = nickname;
    }

    public MemberVO(@JsonProperty("passwd") String passwd,@JsonProperty("email") String email) {
        this.passwd = passwd;
        this.email = email;
    }
}
