package ml.market.cors.domain.member.service;


import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ml.market.cors.domain.member.eSubscriberType;
import ml.market.cors.domain.member.entity.MemberDAO;
import ml.market.cors.domain.security.member.role.MemberRole;
import ml.market.cors.domain.util.mail.eMailAuthenticatedFlag;
import ml.market.cors.domain.util.map.KaKaoMapManagement;
import ml.market.cors.repository.mail.EmailStateRepository;
import ml.market.cors.repository.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

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

        bResult = existNickname(nickname);
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

        if(!emailStateRepository.existsByEmailAndAuthenticatedFlag(email, eMailAuthenticatedFlag.Y)){
            return false;
        }

        int latitude = 0;
        int longitude = 0;
        kaKaoMapManagement.search(memberVo.getAddress());
        MemberDAO memberDAO = new MemberDAO(email, MemberRole.USER, passwd
                ,memberVo.getAddress(), latitude, longitude, nickname, eSubscriberType.NORMAL);
        memberRepository.save(memberDAO);

        return true;
    }
}
