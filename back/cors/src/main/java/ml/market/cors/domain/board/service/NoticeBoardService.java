package ml.market.cors.domain.board.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ml.market.cors.domain.board.entity.Notic_boardDAO;
import ml.market.cors.domain.member.entity.MemberDAO;
import ml.market.cors.repository.board.NoticeBoardRepository;
import ml.market.cors.repository.member.MemberRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeBoardService {
    private final NoticeBoardRepository noticeBoardRepository;

    private final MemberRepository memberRepository;
    public void save(@NonNull String title,@NonNull String content, long memberId){
        if(memberId == 0){
            throw new NullPointerException();
        }


        MemberDAO memberDAO = memberRepository.findById(memberId);
        noticeBoardRepository.save(new Notic_boardDAO(title,content, memberDAO, LocalDateTime.now()));
    }

    public void delete(){

    }
    public void view(){

    }
    public void list(){

    }
}
