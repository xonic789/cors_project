package ml.market.cors.domain.board.service;

import ml.market.cors.domain.board.entity.Report_boardDAO;
import ml.market.cors.domain.board.entity.dto.ReportDTO;
import ml.market.cors.domain.board.entity.dto.ReportForm;
import ml.market.cors.domain.security.member.JwtCertificationToken;

public interface ReportBoardService {

    Report_boardDAO createReportBoard(ReportForm reportForm, JwtCertificationToken jwtCertificationToken) throws IllegalStateException;
    ReportDTO findOne(Long id);
}
