package ml.market.cors.domain.util.map;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import javassist.NotFoundException;
import ml.market.cors.domain.util.map.dto.KakaoResMapDTO;
import org.springframework.stereotype.Component;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@Component
public class KaKaoMapManagement {
    private final String authorization = "KakaoAK 61bd24470a52a968fb0af745df8dc26d";

    private final String searchUrl = "https://dapi.kakao.com/v2/local/search/address.json?query=";
    public KakaoResMapDTO search(String address) {
        KakaoResMapDTO kakaoResMapDTO = null;
        try {
                String apiURL = searchUrl + URLEncoder.encode(address, "UTF-8");
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);
            con.addRequestProperty("Authorization", authorization);
            con.setRequestMethod("GET");
            con.setDoOutput(false);

            if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
                kakaoResMapDTO = objectMapper.readValue(con.getInputStream(), KakaoResMapDTO.class);
            } else {
                throw new NotFoundException("쿼리 실패");
            }

        } catch (Exception e) {
            System.err.println(e.toString());
        }
        return kakaoResMapDTO;
    }

}
