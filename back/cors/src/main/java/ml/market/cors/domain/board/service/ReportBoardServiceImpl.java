package ml.market.cors.domain.board.service;

import lombok.RequiredArgsConstructor;
import ml.market.cors.domain.board.entity.Report_boardDAO;
import ml.market.cors.domain.board.entity.dto.ReportDTO;
import ml.market.cors.domain.board.entity.dto.ReportForm;
import ml.market.cors.domain.member.entity.MemberDAO;
import ml.market.cors.domain.security.member.JwtCertificationToken;
import ml.market.cors.repository.board.ReportBoardRepository;
import ml.market.cors.repository.board.ReportQueryRepository;
import ml.market.cors.repository.member.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportBoardServiceImpl implements ReportBoardService{

    private final ReportBoardRepository reportBoardRepository;
    private final MemberRepository memberRepository;
    private final ReportQueryRepository reportQueryRepository;


    @Override
    @Transactional(readOnly = false)
    public Report_boardDAO createReportBoard(ReportForm reportForm, JwtCertificationToken jwtCertificationToken) throws IllegalStateException{
        MemberDAO findMember = findMember(jwtCertificationToken).orElseThrow(() -> new IllegalStateException());
        Report_boardDAO reportBoard = Report_boardDAO.createReportBoard(reportForm, findMember);
        return reportBoardRepository.save(reportBoard);
    }

    @Override
    public ReportDTO findOne(Long id) {
        return reportQueryRepository.findById(id);
    }

    private Optional<MemberDAO> findMember(JwtCertificationToken jwtCertificationToken) {

        if(jwtCertificationToken!=null){
            return Optional.of(memberRepository.findByEmail(jwtCertificationToken.getName()));
        }
        return Optional.empty();
    }

}
