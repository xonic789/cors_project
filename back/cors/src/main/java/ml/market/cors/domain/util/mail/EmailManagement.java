package ml.market.cors.domain.util.mail;

import com.blue.hosting.entity.account.AccountInfoRepo;
import com.blue.hosting.entity.email.EmailStateDAO;
import com.blue.hosting.entity.email.EmailStateRepo;
import lombok.extern.slf4j.Slf4j;
import ml.market.cors.domain.mail.entity.EmailStateDAO;
import ml.market.cors.repository.mail.EmailStateRepository;
import ml.market.cors.repository.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
public class EmailManagement extends MailTransfer {
    private static final char Y_AUTHENTICATED_FLAG = 'Y';

    private final char NO_AUTHENTICATED_FLAG = 'N';

    private final int CODE_BOUNDRY = 9999;

    private final int EXPIRE_TIME = 1000 * 60 * 3;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private EmailStateRepository emailStateRepository;

    @Scheduled(fixedDelay = 1000 * 60)
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

    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public void insert(String email) throws RuntimeException{
        Random rand = new Random();
        Long expireTime = new Date().getTime() + EXPIRE_TIME;
        try {
            if(mAccountInfoRepo.existsByEmail(email)){
                throw new NoSuchElementException();
            }
            if(mEmailStateRepo.existsById(email)){
                throw new NoSuchElementException();
            }
            int code = rand.nextInt(CODE_BOUNDRY);
            mEmailStateRepo.save(new EmailStateDAO(email, NO_AUTHENTICATED_FLAG, expireTime
                    , code));
            String text = "인증코드: " + code;
            if(super.send(email, "인증메일", text) == false){
                log.debug("인증메일 보내기 실패");
                throw new RuntimeException();
            }
        }catch (Exception e){
            throw new RuntimeException();
        }
    }

    public boolean isCode(int code, String email){
        if(mEmailStateRepo.existsById(email) == false){
            return false;
        }
        try{
            Optional<EmailStateDAO> optional = mEmailStateRepo.findById(email);
            EmailStateDAO emailStateDAO = optional.get();
            if(emailStateDAO.getCode() != code){
                throw new Exception();
            }
            mEmailStateRepo.save(new EmailStateDAO(emailStateDAO.getEmail(), Y_AUTHENTICATED_FLAG, emailStateDAO.getExpireTime(), emailStateDAO.getCode()));
        } catch (Exception e){
            return false;
        }

        return true;
    }
}
