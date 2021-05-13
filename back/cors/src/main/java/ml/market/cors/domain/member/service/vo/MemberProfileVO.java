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

    private String newPasswd;

    private String intro;

    private MultipartFile profile_img;

    private String nickname;
}
