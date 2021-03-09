package ml.market.cors.domain.member.service.vo;



import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;


@RequiredArgsConstructor
@Getter
@ToString
public class MemberJoinVO {
    @NonNull
    private final String passwd;

    @NonNull
    private final String email;

    @NonNull
    private final String address;

    @NonNull
    private final String nickname;
}
