package kb.practiceboard.controller;

import kb.practiceboard.domain.File;
import kb.practiceboard.domain.FileDto;
import kb.practiceboard.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class FileController {
  private FileService fileService;
  private MultipartFile multipartFile;

  @Autowired
  public FileController(FileService fileService) {
    this.fileService = fileService;
  }

  @PostMapping(value = "/file", consumes = {"multipart/form-data"})
  public File uploadFile(@RequestPart("files") MultipartFile files) {
    return fileService.upload(files);
  }

  @GetMapping("/file")
  public List<File> findFile(@RequestBody FileDto fileDto) {
    return fileService.findByPostingId(fileDto);
  }

  @PatchMapping(value = "/file", consumes = {"multipart/form-data"})
  public String updateFile(@RequestPart("files") MultipartFile files) {
    fileService.update(files);
    return "파일이 수정되었습니다.";
  }

  @DeleteMapping("/file")
  public String deleteFile(@RequestBody FileDto fileDto) {
    return fileService.delete(fileDto);
  }

}
