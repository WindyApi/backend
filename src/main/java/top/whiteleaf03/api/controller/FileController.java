package top.whiteleaf03.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import top.whiteleaf03.api.service.file.FileService;

import java.io.IOException;

/**
 * @author WhiteLeaf03
 */
@Controller
@RequestMapping("/download")
public class FileController {
    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("sdk")
    public ResponseEntity<InputStreamResource> downloadSDK() throws IOException {
        return fileService.downloadSDK();
    }
}
