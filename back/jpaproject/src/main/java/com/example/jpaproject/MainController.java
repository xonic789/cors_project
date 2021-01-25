package com.example.jpaproject;

import com.example.jpaproject.entity.MemberRepo;
import com.example.jpaproject.entity.MemberDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@RestController
public class MainController {
    @Autowired
    private MemberRepo memberRepo;
    //isolation = Isolation.READ_UNCOMMITTED
    @RequestMapping("/test")
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public void m() throws InterruptedException {
        memberRepo.save(new MemberDAO(1L, "test"));
        Thread.sleep(5000);
    }

    @RequestMapping("/testt")
    public void a(){
        List list = memberRepo.findAll();
    }
}
