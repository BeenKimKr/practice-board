package kb.practiceboard.controller;

import kb.practiceboard.domain.FileEntity;
import kb.practiceboard.dto.MessageDto;
import kb.practiceboard.dto.file.FileDto;
import kb.practiceboard.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
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
  @ResponseStatus(code = HttpStatus.CREATED)
  public FileDto uploadFile(@RequestPart("files") MultipartFile files) {
    FileEntity newFile = fileService.upload(files);
    FileDto file = FileDto.builder()
        .originalName(newFile.getOriginalName())
        .size(newFile.getSize())
        .mimeType(newFile.getMimeType())
        .build();
    return file;
  }

  @GetMapping("/files")
  public List<FileDto> fileListByPostingId(@RequestBody FileDto fileDto) {
    List<FileEntity> fileList = fileService.findByPostingId(fileDto);
    List<FileDto> files = new ArrayList<>();
    for (FileEntity f : fileList) {
      files.add(FileDto.builder()
          .originalName(f.getOriginalName())
          .size(f.getSize())
          .mimeType(f.getMimeType())
          .uploaderId(f.getUploaderId())
          .build());
    }
    return files;
  }

  @PatchMapping(value = "/file", consumes = {"multipart/form-data"})
  public String updateFile(@RequestPart("files") MultipartFile files,
                           @RequestParam String postingId,
                           @RequestParam String oldFileName) {
    fileService.update(postingId, oldFileName, files);
    return "파일이 수정되었습니다.";
  }

  @DeleteMapping("/file")
  public MessageDto deleteFile(@RequestBody String fileId) {
    MessageDto message = MessageDto.builder()
        .message(fileService.delete(fileId))
        .build();
    return message;
  }

}
