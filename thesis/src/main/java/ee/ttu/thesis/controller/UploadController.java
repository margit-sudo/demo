package ee.ttu.thesis.controller;

import ee.ttu.thesis.domain.File;
import ee.ttu.thesis.service.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/upload")
@RequiredArgsConstructor
public class UploadController {

    private final UploadService uploadService;

    @GetMapping("/all")
    public List<File> getAllFiles(){
        return uploadService.getFilesList();
    }

    @PostMapping("/save")
    public void saveFile(@RequestParam(value = "file") MultipartFile file) throws IOException {
        uploadService.parseAndSaveMultipartFile(file);
    }
}
