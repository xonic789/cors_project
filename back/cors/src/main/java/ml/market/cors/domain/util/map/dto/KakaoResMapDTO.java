package ml.market.cors.domain.util.map.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.List;

@Data
public class KakaoResMapDTO {
    private HashMap<String, Object> meta;
    private List<MapDocumentsDTO> documents;

    public KakaoResMapDTO(@JsonProperty("meta") HashMap<String, Object> meta, @JsonProperty("documents") List<MapDocumentsDTO> documents) {
        this.meta = meta;
        this.documents = documents;
    }
}

