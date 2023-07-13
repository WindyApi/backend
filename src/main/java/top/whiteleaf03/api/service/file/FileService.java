package top.whiteleaf03.api.service.file;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

/**
 * @author WhiteLeaf03
 */
public interface FileService {
    /**
     * 下载sdk
     *
     * @return 返回结果
     * @throws IOException 抛出异常
     */
    ResponseEntity<InputStreamResource> downloadSDK() throws IOException;
}
