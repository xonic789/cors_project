package ml.market.cors.upload;

import ml.market.cors.domain.member.entity.MemberDAO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface Uploader {

    String upload(MultipartFile multipartFile, String dirName, Long id,String dir) throws IOException;
}
