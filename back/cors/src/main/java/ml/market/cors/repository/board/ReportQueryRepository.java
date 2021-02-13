package ml.market.cors.repository.board;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import ml.market.cors.domain.board.entity.QReport_boardDAO;
import ml.market.cors.domain.board.entity.Report_boardDAO;
import ml.market.cors.domain.board.entity.dto.QReportDTO;
import ml.market.cors.domain.board.entity.dto.ReportDTO;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import static ml.market.cors.domain.board.entity.QReport_boardDAO.report_boardDAO;

@Repository
public class ReportQueryRepository {


    private final JPAQueryFactory query;

    public ReportQueryRepository(EntityManager em) {
        this.query=new JPAQueryFactory(em);
    }

    public ReportDTO findById(Long id){
        return query
                .select(new QReportDTO(
                        report_boardDAO.title,
                        report_boardDAO.content,
                        report_boardDAO.member.nickname,
                        report_boardDAO.write_date
                ))
                .from(report_boardDAO)
                .join(report_boardDAO.member).fetchJoin()
                .where(report_boardDAO.report_id.eq(id))
                .fetchOne();
    }

}
