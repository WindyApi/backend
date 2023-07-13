package top.whiteleaf03.api.service.file;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author WhiteLeaf03
 */
@Slf4j
@Service
public class FileServiceImpl implements FileService {
    /**
     * 下载sdk
     *
     * @return 返回结果
     * @throws IOException 抛出异常
     */
    @Override
    public ResponseEntity<InputStreamResource> downloadSDK() throws IOException {
        ClassPathResource resource = new ClassPathResource("WindyApi-sdk-1.0.0.jar");
        InputStream inputStream = resource.getInputStream();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; WindyApi-sdk-1.0.0.jar");
        headers.add(HttpHeaders.CONTENT_TYPE, "application/java-archive");
        headers.add(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate");
        headers.add(HttpHeaders.PRAGMA, "no-cache");
        headers.add(HttpHeaders.EXPIRES, "0");

        InputStreamResource inputStreamResource = new InputStreamResource(inputStream);
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(resource.contentLength())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(inputStreamResource);
    }
}
