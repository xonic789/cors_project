package ml.market.cors.upload.vo;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ImageInfoVO {
    private final String url;
    private final String key;
}
