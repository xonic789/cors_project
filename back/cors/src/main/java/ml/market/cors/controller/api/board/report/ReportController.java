package ml.market.cors.controller.api.board.report;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ml.market.cors.domain.board.entity.Report_boardDAO;
import ml.market.cors.domain.board.entity.dto.ReportForm;
import ml.market.cors.domain.board.service.ReportBoardService;
import ml.market.cors.domain.security.member.JwtCertificationToken;
import ml.market.cors.domain.util.Errors;
import ml.market.cors.domain.util.Message;
import ml.market.cors.domain.util.ResponseEntityUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class ReportController {

    private final ReportBoardService reportBoardService;
    private final ResponseEntityUtils responseEntityUtils;

    @PostMapping("/api/report")
    public ResponseEntity<Message<Object>> createReport(
            @AuthenticationPrincipal JwtCertificationToken jwtCertificationToken,
            @ModelAttribute ReportForm reportForm){
        Report_boardDAO reportBoard =null;
        try{
            reportBoard = reportBoardService.createReportBoard(reportForm, jwtCertificationToken);
        }catch (IllegalStateException e){
            return responseEntityUtils.getMessageResponseEntityUnauthorized(
                    new Errors(
                            "auth",
                            "member",
                            "member eq null",
                            "로그인 해야 합니다."));
        }
        return responseEntityUtils.getMessageResponseEntityCreated(reportBoardService.findOne(reportBoard.getReport_id()));
    }
}
