package ml.market.cors.domain.market.entity.dto;

import lombok.Data;
import lombok.NonNull;
import lombok.Setter;
import ml.market.cors.domain.article.entity.dao.ArticleDAO;
import ml.market.cors.domain.market.enums.MarketStatus;
import ml.market.cors.domain.member.entity.MemberDAO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class MarketApproveStatusUpdateDTO {
    private Long marketId;

    private MemberDAO member;

    private List<ArticleDAO> articles = new ArrayList<>();

    private String name;

    private String intro;

    private String location;

    private double latitude;

    private double longitude;

    private String image;

    private MarketStatus status;

    private String canclecause;

    public MarketApproveStatusUpdateDTO(@NonNull MarketStatus status, String canclecause) {
        this.status = status;
        this.canclecause = canclecause;
    }
}
