package ml.market.cors.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    @PostMapping("/test")
    public String login(){
        return "hell";
    }
}
