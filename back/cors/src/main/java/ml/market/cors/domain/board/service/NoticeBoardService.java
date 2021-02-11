package ml.market.cors.domain.board.service;

import lombok.RequiredArgsConstructor;
import ml.market.cors.domain.board.entity.Notic_boardDAO;
import ml.market.cors.domain.board.enums.eNoticeBoardKey;
import ml.market.cors.domain.member.entity.MemberDAO;
import ml.market.cors.repository.board.NoticeBoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class NoticeBoardService {
    private final NoticeBoardRepository noticeBoardRepository;

    public boolean save(String title, String content, long memberId){
        if(memberId == 0 || title == null || content == null){
            return false;
        }

        MemberDAO memberDAO = new MemberDAO(memberId);
        try{
            noticeBoardRepository.save(new Notic_boardDAO(title, content, LocalDateTime.now(), memberDAO));
        }catch (Exception e){
            return false;
        }
        return true;
    }

    public boolean update(String title, String content, long noticeId, long memberId){
        if(memberId == 0 || noticeId == 0 || title == null || content == null){
            return false;
        }

        MemberDAO memberDAO = new MemberDAO(memberId);
        try{
            noticeBoardRepository.save(new Notic_boardDAO(noticeId, title,content, memberDAO));
        }catch (Exception e){
            return false;
        }
        return true;
    }

    public boolean delete(long noticeId){
        if(noticeId == 0) {
            return false;
        }
        try {
            noticeBoardRepository.deleteById(noticeId);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    public Map<eNoticeBoardKey, String> view(long noticeId){
        if(noticeId == 0){
            return null;
        }
        Map<eNoticeBoardKey, String> noticeBoardItem = new HashMap<>();
        try {
            Notic_boardDAO noticeBoardDAO = noticeBoardRepository.findById(noticeId);
            noticeBoardItem.put(eNoticeBoardKey.noticeId, noticeBoardDAO.getNoticeId().toString());
            noticeBoardItem.put(eNoticeBoardKey.title, noticeBoardDAO.getTitle());
            noticeBoardItem.put(eNoticeBoardKey.content, noticeBoardDAO.getContent());
            noticeBoardItem.put(eNoticeBoardKey.writeDate, noticeBoardDAO.getWriteDate().toString());
            noticeBoardItem.put(eNoticeBoardKey.email, noticeBoardDAO.getMember().getEmail());
        } catch (Exception e){
            return null;
        }
        return noticeBoardItem;
    }

    public List<Map<eNoticeBoardKey, String>> list(Pageable pageable){
        Page<Notic_boardDAO> noticeBoardPage = noticeBoardRepository.findAll(pageable);
        List<Notic_boardDAO> noticeBoardList = noticeBoardPage.getContent();
        List<Map<eNoticeBoardKey, String>> itemList = new ArrayList<>();
        Map<eNoticeBoardKey, String> itemMap;
        for (Notic_boardDAO item : noticeBoardList) {
            itemMap = new HashMap<>();
            itemMap.put(eNoticeBoardKey.noticeId, item.getNoticeId().toString());
            itemMap.put(eNoticeBoardKey.writeDate, item.getWriteDate().toString());
            itemMap.put(eNoticeBoardKey.title, item.getTitle());
            itemList.add(itemMap);
        }
        itemMap = new HashMap<>();
        itemMap.put(eNoticeBoardKey.pageTotal, Integer.toString(noticeBoardPage.getTotalPages()));
        itemList.add(itemMap);
        return itemList;
    }
}
