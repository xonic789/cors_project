package ml.market.cors.domain.util.map;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;
import java.util.List;

@Data
public class KakaoResMapVO {
    private HashMap<String, Object> meta;
    private List<Documents> documents;

    public KakaoResMapVO(@JsonProperty("meta") HashMap<String, Object> meta, @JsonProperty("documents") List<Documents> documents) {
        this.meta = meta;
        this.documents = documents;
    }
}

