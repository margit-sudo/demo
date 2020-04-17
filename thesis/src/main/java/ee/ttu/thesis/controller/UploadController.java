package ee.ttu.thesis.controller;

import ee.ttu.thesis.domain.File;
import ee.ttu.thesis.service.UploadService;
import ee.ttu.thesis.service.UserService;
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
    private final UserService userService;

    @GetMapping("/all")
    public List<File> getAllFilesByUserId(@RequestHeader(name = "Authorization") String token){
        return uploadService.getFilesListByUserId(userService.getUserIdFromToken(token));
    }

    @PostMapping("/save")
    public void saveFile(@RequestParam(value = "file") MultipartFile file, @RequestHeader(name = "Authorization") String token) throws IOException {
        uploadService.parseAndSaveMultipartFile(file, userService.getUserFromToken(token));
    }
}
