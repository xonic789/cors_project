package ml.market.cors.domain.util.map;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javassist.NotFoundException;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.net.http.HttpResponse;

@Component
public class KaKaoMapManagement {
    private final String authorization = "KakaoAK 61bd24470a52a968fb0af745df8dc26d";

    private final String searchUrl = "https://dapi.kakao.com/v2/local/search/address.json?query=";

    public KakaoResMapVO search(String address) {
        KakaoResMapVO kakaoResMapVO = null;
        try {
                String apiURL = searchUrl + URLEncoder.encode(address, "UTF-8");
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setConnectTimeout(5000); //서버에 연결되는 Timeout 시간 설정
            con.setReadTimeout(5000); // InputStream 읽어 오는 Timeout 시간 설정
            con.addRequestProperty("Authorization", authorization); //key값 설정
            con.setRequestMethod("GET");
            con.setDoOutput(false);

            if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
                kakaoResMapVO = objectMapper.readValue(con.getInputStream(), KakaoResMapVO.class);
            } else {
                throw new NotFoundException("쿼리 실패");
            }

        } catch (Exception e) {
            System.err.println(e.toString());
        }
        return kakaoResMapVO;
    }
}
