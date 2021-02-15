package ml.market.cors.controller.api.board.question;


import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class QuestionController {
    @PostMapping("/question/save")
    public void saveArticle(){

    }

    @GetMapping("/question/approve/list")
    public void findAllArticle(){

    }

    @PostMapping("/question/approve/delete")
    public void deleteArticle(){

    }

    @PostMapping("/question/approve/comment/save")
    public void saveComment(){

    }

    @PutMapping("/question/approve/comment/update")
    public void updateComment(){

    }

    @PostMapping("/question/approve/comment/delete")
    public void deleteComment(){

    }

}
