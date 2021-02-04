package ml.market.cors.domain.member.service;


import com.google.common.io.ByteStreams;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ml.market.cors.domain.mail.entity.EmailStateDAO;
import ml.market.cors.domain.member.entity.MemberDAO;
import ml.market.cors.domain.member.map.MemberParam;
import ml.market.cors.domain.security.member.role.MemberRole;
import ml.market.cors.domain.security.oauth.enu.SocialType;
import ml.market.cors.domain.util.mail.eMailAuthenticatedFlag;
import ml.market.cors.domain.util.kakao.dto.map.MapDocumentsDTO;
import ml.market.cors.domain.util.kakao.KaKaoRestManagement;
import ml.market.cors.domain.util.kakao.dto.map.KakaoResMapDTO;
import ml.market.cors.repository.mail.EmailStateRepository;
import ml.market.cors.repository.member.MemberRepository;
import ml.market.cors.upload.S3Uploader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberManagement {
    private final MemberRepository memberRepository;

    private final EmailStateRepository emailStateRepository;

    private final KaKaoRestManagement kaKaoRestManagement;

    private final S3Uploader s3Uploader;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public boolean change(@NonNull Map<String, Object> member, Long id, MultipartFile multipartFile){
        if(id == 0){
            return false;
        }
        String passwd = (String)member.get(MemberParam.PASSWD);
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

        if(member.containsKey(MemberParam.NICKNAME)) {
            String nickname = (String) member.get(MemberParam.NICKNAME);
            if (nickname.equals("")) {
                return false;
            }
            if (!existNickname(nickname, id)) {
                if (existNickname(nickname)) {
                    return false;
                }
                memberDAO = memberRepository.save(new MemberDAO(memberDAO.getProfileKey(), id, memberDAO.getProfile_img(), memberDAO.getEmail(), memberDAO.getRole(), memberDAO.getPassword(), memberDAO.getAddress(), memberDAO.getLatitude(), memberDAO.getLongitude(), nickname, memberDAO.getSocialType()));
            }
        }

        if(member.containsKey(MemberParam.NEWPASSWD)){
            String newPasswd = (String) member.get(MemberParam.NEWPASSWD);
            if(!newPasswd.equals("")){
                newPasswd = bCryptPasswordEncoder.encode(newPasswd);
                memberDAO = memberRepository.save(new MemberDAO(memberDAO.getProfileKey(), id, memberDAO.getProfile_img(), memberDAO.getEmail(), memberDAO.getRole(), newPasswd, memberDAO.getAddress(), memberDAO.getLatitude(), memberDAO.getLongitude(), memberDAO.getNickname(), memberDAO.getSocialType()));
            }
        }

        if(!multipartFile.isEmpty()){
            try{
                String fileName = System.currentTimeMillis() + multipartFile.getOriginalFilename();
                Map<String, String> result = s3Uploader.upload(multipartFile, memberDAO.getEmail() ,System.currentTimeMillis(), multipartFile.getOriginalFilename(), fileName, memberDAO.getProfileKey());
                if(result == null){
                    return true;
                }
                s3Uploader.deleteObject(memberDAO.getProfileKey());
                memberDAO = memberRepository.save(new MemberDAO(result.get("key"), id, result.get("url"), memberDAO.getEmail(), memberDAO.getRole(), memberDAO.getPassword(), memberDAO.getAddress(), memberDAO.getLatitude(), memberDAO.getLongitude(), memberDAO.getNickname(), memberDAO.getSocialType()));
            }catch (Exception e){
                log.debug("파일 업로드 실패");
                throw new RuntimeException();
            }
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

    public boolean existNickname(@NonNull String nickname, long id){
        if(id == 0){
            return false;
        }
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
        MemberDAO memberDAO = new MemberDAO(MemberParam.DEFAULT_PROFILE_KEY, email, MemberParam.DEFAULT_PROFILE_IMG_DIR, MemberRole.USER, passwd
                ,address, latitude, longitude, nickname, SocialType.NORMAL);
        memberRepository.save(memberDAO);
        emailStateRepository.deleteById(email);
        return true;
    }

    public Map<String, String> viewProfile(@NonNull HttpServletResponse response, long memberId){
        if(memberId == 0){
            return null;
        }

        List<MemberDAO> memberList = memberRepository.findByMemberId(memberId);
        if(memberList == null){
            return null;
        }
        MemberDAO memberDAO = memberList.get(0);

        Map result = new HashMap();
        result.put(MemberParam.NICKNAME, memberDAO.getNickname());
        result.put(MemberParam.PROFILE_IMG, memberDAO.getProfile_img());
        return result;
    }
}
