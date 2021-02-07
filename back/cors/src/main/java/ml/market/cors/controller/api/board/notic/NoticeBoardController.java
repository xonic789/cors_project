package ml.market.cors.controller.api.board.notic;

import ml.market.cors.domain.util.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class NoticeBoardController {
    public ResponseEntity<Message<Object>> save(){
        ResponseEntity<Message<Object>> messageResponseEntity = null;
        return messageResponseEntity;
    }

    public ResponseEntity<Message<Object>> view(){
        ResponseEntity<Message<Object>> messageResponseEntity = null;

        return messageResponseEntity;
    }

    public ResponseEntity<Message<Object>> delete(){
        ResponseEntity<Message<Object>> messageResponseEntity = null;
        return messageResponseEntity;
    }

    public ResponseEntity<Message<Object>> readList(){
        ResponseEntity<Message<Object>> messageResponseEntity = null;
        return messageResponseEntity;
    }
}
