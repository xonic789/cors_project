package ml.market.cors.controller.api.article;


import lombok.RequiredArgsConstructor;
import ml.market.cors.domain.article.service.ArticleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

}
