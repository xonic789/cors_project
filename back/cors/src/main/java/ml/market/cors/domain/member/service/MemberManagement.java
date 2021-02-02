package ml.market.cors.domain.member.service;


import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ml.market.cors.domain.mail.entity.EmailStateDAO;
import ml.market.cors.domain.member.entity.MemberDAO;
import ml.market.cors.domain.member.enu.eMemberParam;
import ml.market.cors.domain.security.member.role.MemberRole;
import ml.market.cors.domain.security.oauth.enu.SocialType;
import ml.market.cors.domain.util.mail.eMailAuthenticatedFlag;
import ml.market.cors.domain.util.kakao.dto.map.MapDocumentsDTO;
import ml.market.cors.domain.util.kakao.KaKaoRestManagement;
import ml.market.cors.domain.util.kakao.dto.map.KakaoResMapDTO;
import ml.market.cors.repository.mail.EmailStateRepository;
import ml.market.cors.repository.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberManagement {
    private final MemberRepository memberRepository;

    private final EmailStateRepository emailStateRepository;

    private final KaKaoRestManagement kaKaoRestManagement;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public boolean change(@NonNull Map<eMemberParam, String> member, @NonNull long id){
        String passwd = member.get(eMemberParam.PASSWD);
        if(passwd == null){
            return false;
        }

        List<MemberDAO> members = memberRepository.findByMemberId(id);
        if(members == null){
            return false;
        }
        MemberDAO memberDAO = members.get(0);
        if (!bCryptPasswordEncoder.matches(passwd, memberDAO.getPassword())) {
            return false;
        }

        String nickname = member.get(eMemberParam.NICKNAME);
        try {
            if (!existNickname(nickname, id)) {
                if (existNickname(nickname)) {
                    return false;
                }
                memberDAO = memberRepository.save(new MemberDAO(id, memberDAO.getEmail(), memberDAO.getRole(), memberDAO.getPassword(), memberDAO.getAddress(), memberDAO.getLatitude(), memberDAO.getLongitude(), nickname, memberDAO.getSocialType()));
            }
        }catch (RuntimeException e){
            throw new RuntimeException();
        }

        String newPasswd = member.get(eMemberParam.NEWPASSWD);
        if(newPasswd != null){
            newPasswd = bCryptPasswordEncoder.encode(newPasswd);
            memberRepository.save(memberRepository.save(new MemberDAO(id, memberDAO.getEmail(), memberDAO.getRole(), newPasswd, memberDAO.getAddress(), memberDAO.getLatitude(), memberDAO.getLongitude(), memberDAO.getNickname(), memberDAO.getSocialType())));
        }
        return true;
    }


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

    public boolean existNickname(@NonNull String nickname, @NonNull long id){
        return memberRepository.existsByMemberIdAndNickname(id, nickname);
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


        String address = memberVo.getAddress();
        KakaoResMapDTO kakaoResMapDTO = kaKaoRestManagement.transAddressToCoordinate(memberVo.getAddress());
        if(kakaoResMapDTO == null){
            return false;
        }
        MapDocumentsDTO mapResMapDocumentsDTO = kakaoResMapDTO.getDocuments().get(0);
        double latitude = mapResMapDocumentsDTO.getY();
        double longitude = mapResMapDocumentsDTO.getX();
        MemberDAO memberDAO = new MemberDAO(email, MemberRole.USER, passwd
                ,address, latitude, longitude, nickname, SocialType.NORMAL);
        memberRepository.save(memberDAO);
        emailStateRepository.deleteById(email);
        return true;
    }
}
