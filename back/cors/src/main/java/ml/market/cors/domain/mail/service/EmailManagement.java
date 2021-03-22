package ml.market.cors.domain.mail.service;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import ml.market.cors.domain.mail.entity.EmailStateDAO;
import ml.market.cors.domain.mail.vo.MailVO;
import ml.market.cors.domain.util.mail.MailTransfer;
import ml.market.cors.domain.util.mail.eMailAuthenticatedFlag;
import ml.market.cors.repository.mail.EmailStateRepository;
import ml.market.cors.repository.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityExistsException;
import java.util.*;

@Service
public class EmailManagement extends MailTransfer {
    private final int CODE_BOUNDRY = 9999;

    private final int EXPIRE_TIME = 1000 * 60 * 3;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private EmailStateRepository emailStateRepository;


    @Scheduled(fixedDelay = 1000 * 60 * 20)
    @Transactional(isolation = Isolation.SERIALIZABLE)
    protected void cleaner(){
        List<EmailStateDAO> waiters = emailStateRepository.findAll();
        Date date = new Date();
        for (EmailStateDAO emailStateDAO : waiters) {
            if(date.after(new Date(emailStateDAO.getExpireTime())) == false){
                continue;
            }
            emailStateRepository.deleteById(emailStateDAO.getEmail());
        }
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void insert(@NonNull String email) throws RuntimeException{
        Random rand = new Random();
        Long expireTime = new Date().getTime() + EXPIRE_TIME;
        if(memberRepository.existsByEmail(email)){
            throw new RuntimeException();
        }
        try {
            if(emailStateRepository.existsById(email)){
                Optional<EmailStateDAO> optional = emailStateRepository.findById(email);
                EmailStateDAO emailStateDAO = optional.get();
                Date date = new Date();
                if(date.after(new Date(emailStateDAO.getExpireTime()))){
                    emailStateRepository.deleteById(email);
                }else{
                    throw new RuntimeException();
                }
            }
            int code = rand.nextInt(CODE_BOUNDRY);
            String text = "인증코드: " + code;
            if(super.send(email, "인증메일", text) == false){
                throw new RuntimeException();
            }
            emailStateRepository.save(new EmailStateDAO(email, eMailAuthenticatedFlag.N, expireTime
                    , code));
        }catch (Exception e){
            throw new RuntimeException();
        }
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public boolean checkCode(@NonNull MailVO mailVO){
        if(!emailStateRepository.existsById(mailVO.getEmail())){
            return false;
        }
        try{
            Optional<EmailStateDAO> optional = emailStateRepository.findById(mailVO.getEmail());
            EmailStateDAO emailStateDAO = optional.get();
            if(emailStateDAO.getCode() != mailVO.getCode()){
                throw new Exception();
            }
            emailStateRepository.save(new EmailStateDAO(emailStateDAO.getEmail(), eMailAuthenticatedFlag.Y, emailStateDAO.getExpireTime(), emailStateDAO.getCode()));
        } catch (Exception e){
            return false;
        }

        return true;
    }
}
