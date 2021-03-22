package ml.market.cors.domain.board.service;

import lombok.RequiredArgsConstructor;
import ml.market.cors.domain.board.entity.QuestionBoardDAO;
import ml.market.cors.domain.board.entity.dto.QuestionBoardDTO;
import ml.market.cors.domain.board.entity.dto.QuestionMemberDTO;
import ml.market.cors.domain.board.entity.dto.QuestionViewDTO;
import ml.market.cors.domain.comment.entity.CommentBoardDAO;
import ml.market.cors.domain.comment.entity.dto.CommentBoardDTO;
import ml.market.cors.domain.member.entity.MemberDAO;
import ml.market.cors.domain.security.member.role.MemberGrantAuthority;
import ml.market.cors.domain.security.member.role.MemberRole;
import ml.market.cors.repository.board.QuestionRepository;
import ml.market.cors.repository.comment.CommentRepository;
import ml.market.cors.repository.member.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;

    private final MemberRepository memberRepository;

    private final CommentRepository commentRepository;

    public QuestionMemberDTO getMyQuestion(int pageIndex, long memberId, String email) {
        if(memberId == 0 || email == null){
            return null;
        }
        Pageable pageable = PageRequest.of(pageIndex, 10);
        Page<QuestionBoardDAO> page = questionRepository.findAllByMember(pageable, memberId);

        List<QuestionBoardDAO> contentList = page.getContent();
        int totalPage = page.getTotalPages();
        List<QuestionBoardDTO> memberQuestionList = new ArrayList<>();
        QuestionBoardDTO questionBoardDTO;
        for (QuestionBoardDAO item : contentList) {
            questionBoardDTO = new QuestionBoardDTO(item.getQuestionId(), item.getTitle(), item.getContent(), item.getWriteDate(), email);
            memberQuestionList.add(questionBoardDTO);
        }
        QuestionMemberDTO questionMemberDTO = new QuestionMemberDTO(totalPage, memberQuestionList);
        return questionMemberDTO;
    }

    public QuestionMemberDTO getAllQuestion(int pageIndex) {
        Pageable pageable = PageRequest.of(pageIndex, 10);
        Page<QuestionBoardDAO> page = questionRepository.findAll(pageable);
        List<QuestionBoardDAO> contentList = page.getContent();
        List<QuestionBoardDTO> questionBoardDTOList = new ArrayList<>();
        QuestionBoardDTO questionBoardDTO;
        MemberDAO memberDAO;
        for (QuestionBoardDAO item : contentList) {
             memberDAO = questionRepository.findByQuestionId(item.getQuestionId());
            questionBoardDTO = new QuestionBoardDTO(item.getQuestionId(), item.getTitle(), item.getContent(), item.getWriteDate(), memberDAO.getEmail());
            questionBoardDTOList.add(questionBoardDTO);
        }
        int totalPage = page.getTotalPages();
        QuestionMemberDTO questionMemberDTO = new QuestionMemberDTO(totalPage, questionBoardDTOList);
        return questionMemberDTO;
    }

    public boolean save(String title, String content, long memberId) {
        if(title == null || content == null || memberId == 0){
            return false;
        }
        if(title.equals("") || content.equals("")){
            return false;
        }
        MemberDAO memberDAO = new MemberDAO(memberId);
        try {
            questionRepository.save(new QuestionBoardDAO(title, content, LocalDateTime.now(), memberDAO));
        } catch (Exception e){
            return false;
        }
        return true;
    }

    public boolean delete(long questionId) {
        if(questionId == 0){
            return false;
        }
        try {
            questionRepository.deleteById(questionId);
        } catch (Exception e){
            return false;
        }
        return true;
    }

    public QuestionViewDTO view(int pageIndex, List roles, long questionId, long memberId) {
        if(roles == null || questionId == 0 || memberId == 0){
            return null;
        }
        MemberGrantAuthority memberGrant = (MemberGrantAuthority) roles.get(0);
        String role = memberGrant.getAuthority();
        boolean bResult;
        if(!role.equals("ROLE_ADMIN")){
            bResult = questionRepository.existsByQuestionIdAndMemberId(questionId, memberId);
            if(!bResult){
                return null;
            }
        }
        QuestionBoardDAO questionBoardDAO = questionRepository.getOne(questionId);
        if(questionBoardDAO == null){
            return null;
        }
        Pageable pageable = PageRequest.of(pageIndex, 10);
        Page<CommentBoardDAO> page = commentRepository.findAllByQuestion(questionBoardDAO, pageable);
        List<CommentBoardDAO> commentContent = page.getContent();
        int totalPage = page.getTotalPages();
        List<CommentBoardDTO> commentList = new ArrayList<>();
        CommentBoardDTO commentBoardDTO;
        MemberDAO memberDAO;
        for (CommentBoardDAO item : commentContent) {
            memberDAO = memberRepository.getOne(memberId);
            commentBoardDTO = new CommentBoardDTO(item.getCommentId(), item.getContent(),item.getWriteDate(), memberDAO.getNickname());
            commentList.add(commentBoardDTO);
        }

        QuestionViewDTO questionViewDTO = new QuestionViewDTO(totalPage, questionBoardDAO.getQuestionId(), questionBoardDAO.getContent(), questionBoardDAO.getTitle(), commentList);
        return questionViewDTO;
    }
}
