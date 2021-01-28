package ml.market.cors.domain.member.service;


import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ml.market.cors.domain.mail.entity.EmailStateDAO;
import ml.market.cors.domain.mail.vo.MailVO;
import ml.market.cors.domain.member.eSubscriberType;
import ml.market.cors.domain.member.entity.MemberDAO;
import ml.market.cors.domain.security.member.role.MemberRole;
import ml.market.cors.domain.util.mail.eMailAuthenticatedFlag;
import ml.market.cors.domain.util.map.Documents;
import ml.market.cors.domain.util.map.KaKaoMapManagement;
import ml.market.cors.domain.util.map.KakaoResMapVO;
import ml.market.cors.repository.mail.EmailStateRepository;
import ml.market.cors.repository.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberManagement {
    private final MemberRepository memberRepository;

    private final EmailStateRepository emailStateRepository;

    private final KaKaoMapManagement kaKaoMapManagement;
    public boolean conditionJoin(String email, String nickname){
        boolean bResult = existNickname(email);
        if(bResult){
            return false;
        }

        bResult = existEmail(nickname);
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
            //시간 다됨
            return false;
        }

        double latitude = 0;
        double longitude = 0;
        KakaoResMapVO kakaoResMapVO = kaKaoMapManagement.search(memberVo.getAddress());
        Documents mapResDocuments = kakaoResMapVO.getDocuments().get(0);
        latitude = mapResDocuments.getY();
        longitude = mapResDocuments.getX();
        MemberDAO memberDAO = new MemberDAO(email, MemberRole.USER, passwd
                ,memberVo.getAddress(), latitude, longitude, nickname, eSubscriberType.NORMAL);
        memberRepository.save(memberDAO);
        emailStateRepository.deleteById(email);
        return true;
    }
}
