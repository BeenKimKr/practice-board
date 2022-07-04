package kb.practiceboard.controller;

import kb.practiceboard.domain.FileEntity;
import kb.practiceboard.service.FileService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileController {
  private FileService fileService;
  private MultipartFile multipartFile;

  @Autowired
  public FileController(FileService fileService) {
    this.fileService = fileService;
  }

  @PostMapping(value = "/file", consumes = {"multipart/form-data"})
  public FileEntity uploadFile(@RequestPart("files") MultipartFile files) {
    return fileService.upload(files);
  }

  @GetMapping("/file")
  public FileEntity findFile(@RequestBody String postingId) {
    return fileService.findByPostingId(postingId);
  }

  @PatchMapping(value = "/file", consumes = {"multipart/form-data"})
  public String updateFile(@RequestPart("files") MultipartFile files,
                           @RequestParam String postingId,
                           @RequestParam String oldFileName) {
    fileService.update(postingId, oldFileName, files);
    return "파일이 수정되었습니다.";
  }

  @DeleteMapping("/file")
  public String deleteFile(@RequestBody ObjectId fileId) {
    return fileService.delete(fileId);
  }

}
