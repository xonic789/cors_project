package ml.market.cors.upload;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ml.market.cors.upload.vo.ImageInfoVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import org.springframework.web.multipart.MultipartFile;
import java.io.*;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3Uploader implements Uploader{

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;

    @Override
    public String upload(MultipartFile multipartFile, String dirName,Long id,String dir) throws Exception {
        File convertedFile = convert(multipartFile).orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File로 전환이 실패하였습니다."));
        return upload(convertedFile,dirName,id,dir);
    }

    public Map<String, String> upload(MultipartFile multipartFile, String dirName, Long id, String dir, String fileName) throws IOException {
        Map<String, Object> map = convert(multipartFile, fileName);
        File convertedFile = (File) map.get("file");
        String uploadFileKey = (String)map.get("key");

        Map<String, String> result = new HashMap<>();
        String url = upload(convertedFile,dirName,id,dir);
        result.put("url", url);
        result.put("key", uploadFileKey);
        return result;
    }

    public ImageInfoVO upload(MultipartFile multipartFile, String dirName, Long id, String dir, String fileName, String key) throws IOException {
        Map<String, Object> map = convert(multipartFile, fileName);
        File convertedFile = (File) map.get("file");
        String uploadFileKey = (String)map.get("key");
        if(key.equals(uploadFileKey)){
            removeNewFile(convertedFile);
            return null;
        }

        String url = upload(convertedFile,dirName,id,dir);
        ImageInfoVO profileImageInfo = new ImageInfoVO(url,uploadFileKey);
        return profileImageInfo;
    }

    private String upload(File uploadFile, String dirName,Long id,String dir) {
        String fileName = dirName + "/" + dir + "/" + id + "/" + uploadFile.getName();
        String uploadImageUrl = putS3(uploadFile, fileName);
        removeNewFile(uploadFile);
        return uploadImageUrl;
    }


    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }


    public void deleteObject(String key){
        amazonS3Client.deleteObject(bucket, key);
    }

    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            return;
        }
        log.debug("임시 파일이 삭제 되지 못했습니다. 파일 이름: {}", targetFile.getName());
    }


    private Optional<File> convert(MultipartFile file) throws IOException{
        File convertFile = new File(file.getOriginalFilename());
        if(convertFile.createNewFile()){
            try(FileOutputStream fos = new FileOutputStream(convertFile)){
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }
       return Optional.empty();
    }



    private Map<String, Object> convert(MultipartFile file, String fileName) throws IOException {
        File convertFile = new File(fileName);
        Map<String, Object> map = new HashMap<>();
        map.put("file", convertFile);
        if (!convertFile.createNewFile()) {
            removeNewFile(convertFile);
            throw new IllegalArgumentException((String.format("파일 변환이 실패했습니다. 파일 이름: %s", file.getName())));
        }
        try {
            FileOutputStream uploadFileOutputStream = new FileOutputStream(convertFile);
            uploadFileOutputStream.write(file.getBytes());
            uploadFileOutputStream.close();

            FileInputStream uploadFileInputStream = new FileInputStream(convertFile);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(uploadFileInputStream);
            String hash = DigestUtils.sha256Hex(bufferedInputStream);
            uploadFileInputStream.close();
            bufferedInputStream.close();
            map.put("key", hash);
        } catch (Exception e) {
            removeNewFile(convertFile);
            throw new IllegalArgumentException((String.format("파일 변환이 실패했습니다. 파일 이름: %s", file.getName())));
        }
        return map;
    }

}
