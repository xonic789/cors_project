package ml.market.cors.domain.member.service.vo;


import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;


@Data
@Getter
public class MemberProfileVO {
    @NonNull
    private final String passwd;

    private String newpasswd;

    private String intro;

    private MultipartFile image;

    private String nickname;
}
