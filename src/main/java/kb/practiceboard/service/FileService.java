package kb.practiceboard.service;

import kb.practiceboard.domain.FileEntity;
import kb.practiceboard.dto.FileDto;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class FileService {

  private MongoTemplate mongoTemplate;
  private MultipartFile multipartFile;

  @Autowired
  public void setMongoTemplate(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Transactional
  public FileEntity upload(MultipartFile files) {
    String originalName = files.getOriginalFilename();
    String currentDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    FileEntity newFile = FileEntity.builder()
        .originalName(originalName)
        .size(files.getSize())
        .mimeType(files.getContentType())
        .uploaderId("5e011ae8-4c9c-4061-a474-5285b7ff3c69")
        .postingId("62bea9f0fb954b34b82cfd36")
        .uploadDateTime(currentDateTime)
        .build();

    return mongoTemplate.insert(newFile, "file");
  }

  public List<FileEntity> findByPostingId(FileDto fileDto) {
    Query query = new Query();
    query.addCriteria(Criteria.where("postingId").is(fileDto.getPostingId()));
    return mongoTemplate.find(query, FileEntity.class, "file");
  }

  public void update(MultipartFile files) {
    Query query = new Query();
    query.addCriteria(Criteria.where("id").is("442fb8e9-5850-4d16-81be-e2d1e260ff78"));

    String currentDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    Update update = new Update();
    update
        .set("originalName", files.getOriginalFilename())
        .set("mineType", files.getContentType())
        .set("uploaderId", "c0da61d6-f259-412b-b6d6-2c0a890c7744")
        .set("postingId", "62be722927bc640846e4a62b")
        .set("uploadDateTime", currentDateTime);
    mongoTemplate.updateMulti(query, update, "file");
    return;
  }

  public String delete(ObjectId fileId) {
    Query query = new Query();
    query.addCriteria(Criteria.where("_id").is(fileId));
    mongoTemplate.remove(query, FileEntity.class, "file");
    return "파일이 삭제되었습니다.";
  }

  public void deleteByPostingId(String postingId) {
    Query query = new Query();
    query.addCriteria(Criteria.where("postingId").is(postingId));
    mongoTemplate.findAllAndRemove(query, FileEntity.class, "file");
    return;
  }
}
