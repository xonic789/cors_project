package ml.market.cors.domain.article.service;

import ml.market.cors.domain.security.member.service.MemberLoginAuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class TestClass {

    @Autowired
    private MemberLoginAuthService memberLoginAuthService;

    @Test
    public void test(){
        System.out.println("helloww");
        memberLoginAuthService.loadUserByUsername("ehaakdl@naver.com");
    }
}
