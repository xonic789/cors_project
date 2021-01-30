package ml.market.cors.domain.member.service;


import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ml.market.cors.domain.mail.entity.EmailStateDAO;
import ml.market.cors.domain.member.entity.MemberDAO;
import ml.market.cors.domain.security.member.role.MemberRole;
import ml.market.cors.domain.security.oauth.enu.SocialType;
import ml.market.cors.domain.util.mail.eMailAuthenticatedFlag;
import ml.market.cors.domain.util.kakao.dto.map.MapDocumentsDTO;
import ml.market.cors.domain.util.kakao.KaKaoRestManagement;
import ml.market.cors.domain.util.kakao.dto.map.KakaoResMapDTO;
import ml.market.cors.repository.mail.EmailStateRepository;
import ml.market.cors.repository.member.MemberRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberManagement {
    private final MemberRepository memberRepository;

    private final EmailStateRepository emailStateRepository;

    private final KaKaoRestManagement kaKaoRestManagement;
    public boolean conditionJoin(String email, String nickname){
        boolean bResult = existNickname(nickname);
        if(bResult){
            return false;
        }

        bResult = existEmail(email);
        if(bResult){
            return false;
        }
        return true;
    }

    public boolean existNickname(@NonNull String nickname){
        return memberRepository.existsByNickname(nickname);
    }

    public boolean existEmail(String email){
        return memberRepository.existsByEmail(email);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public boolean create(MemberVO memberVo) throws RuntimeException {
        String email = memberVo.getEmail();
        String nickname = memberVo.getNickname();
        if(!conditionJoin(email, nickname)){
            return false;
        }

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String passwd = bCryptPasswordEncoder.encode(memberVo.getPasswd());

        Optional<EmailStateDAO> optional = emailStateRepository.findById(email);
        EmailStateDAO emailStateDAO = optional.get();
        if(emailStateDAO.getAuthenticatedFlag() == eMailAuthenticatedFlag.N){
            return false;
        }
        Date date = new Date();
        if(date.after(new Date(emailStateDAO.getExpireTime()))){
            return false;
        }

        double latitude = 0;
        double longitude = 0;
        String address = memberVo.getAddress();
        KakaoResMapDTO kakaoResMapDTO = kaKaoRestManagement.transAddressToCoordinate(memberVo.getAddress());
        if(kakaoResMapDTO == null){
            return false;
        }
        MapDocumentsDTO mapResMapDocumentsDTO = kakaoResMapDTO.getDocuments().get(0);
        latitude = mapResMapDocumentsDTO.getY();
        longitude = mapResMapDocumentsDTO.getX();
        MemberDAO memberDAO = new MemberDAO(email, MemberRole.USER, passwd
                ,address, latitude, longitude, nickname, SocialType.NORMAL);
        memberRepository.save(memberDAO);
        emailStateRepository.deleteById(email);
        return true;
    }
}
