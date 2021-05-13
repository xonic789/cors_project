package ml.market.cors.domain.member.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@RequiredArgsConstructor
@Data
public class MemberVO {
    private long member_id;

    private int longtitude;

    private int latitude;

    private String passwd;

    private String email;

    private Collection<? extends GrantedAuthority> roles;

    private String address;

    private String nickname;
}
