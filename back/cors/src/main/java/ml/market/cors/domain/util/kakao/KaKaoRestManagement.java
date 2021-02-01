package ml.market.cors.domain.util.kakao;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import ml.market.cors.domain.util.kakao.dto.account.KakaoAccountDocumentsDTO;
import ml.market.cors.domain.util.kakao.dto.map.KakaoResMapDTO;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;
import java.util.List;

@Component
public class KaKaoRestManagement {
    private final String clientKey = "KakaoAK 61bd24470a52a968fb0af745df8dc26d";

    private final String KAKAO_REQ_ADDRESS_TRANS_COOR = "https://dapi.kakao.com/v2/local/search/address.json?query=";

    private InputStream send(@NonNull String req_url, String url_param, List<String> header_names, List<String> header_values, @NonNull String req_method) {
        InputStream inputStream = null;
        try {
            StringBuilder urlBuilder = new StringBuilder(req_url);
            if (url_param != null) {
                urlBuilder.append(URLEncoder.encode(url_param, "UTF-8"));
            }
            URL url = new URL(urlBuilder.toString());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);
            if (header_names != null && header_values != null) {
                for (String name : header_names) {
                    for (String value : header_values) {
                        con.setRequestProperty(name, value);
                    }
                }
            }
            con.setRequestMethod(req_method);
            con.setDoOutput(false);

            if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = con.getInputStream();
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            return null;
        }
        return inputStream;
    }

    public KakaoResMapDTO transAddressToCoordinate(String address) {
        KakaoResMapDTO kakaoResMapDTO = null;
        List header_names = new LinkedList();
        header_names.add("Authorization");
        List header_values = new LinkedList();
        header_values.add(clientKey);
        InputStream inputStream = send(KAKAO_REQ_ADDRESS_TRANS_COOR, address, header_names, header_values, "GET");
        if (inputStream == null) {
            return null;
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
            kakaoResMapDTO = objectMapper.readValue(inputStream, KakaoResMapDTO.class);
        } catch (Exception e) {
            return null;
        }
        return kakaoResMapDTO;
    }
}
