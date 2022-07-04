package kb.practiceboard.service;

import kb.practiceboard.domain.CommentEntity;
import kb.practiceboard.dto.comment.CommentCreateDto;
import kb.practiceboard.dto.comment.CommentUpdateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class CommentService {
  private MongoTemplate mongoTemplate;

  public CommentService(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Autowired
  public void setMongoTemplate(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  public List<CommentEntity> findByPostingId(String postingId) {
    Query query = new Query();
    query.addCriteria(Criteria.where("postingId").is(postingId));
    List<CommentEntity> commentList = mongoTemplate.find(query, CommentEntity.class, "comment");
    return commentList;
  }

  public List<CommentEntity> findByWriterId(String writerId) {
    Query query = new Query();
    query.addCriteria(Criteria.where("writerId").is(writerId));
    List<CommentEntity> commentList = mongoTemplate.find(query, CommentEntity.class, "comment");
    return commentList;
  }

  @Transactional
  public CommentEntity create(CommentCreateDto commentCreateDto) {
    String currentDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    CommentEntity comment = CommentEntity.builder()
        .writerId(commentCreateDto.getWriterId())
        .contents(commentCreateDto.getContents())
        .postingId(commentCreateDto.getPostingId())
        .createdDateTime(currentDateTime)
        .updatedDateTime(currentDateTime)
        .build();

    return mongoTemplate.insert(comment, "comment");
  }

  @Transactional
  public String update(String _id,
                       CommentUpdateDto commentUpdateDto) {
    Query query = new Query();
    query.addCriteria(Criteria.where("_id").is(_id));

    Update update = new Update();
    update.set("contents", commentUpdateDto.getContents());

    String currentDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    update.set("updatedDateTime", currentDateTime);

    mongoTemplate.updateMulti(query, update, "comment");
    return "댓글이 수정되었습니다.";
  }

  @Transactional
  public String delete(String commentId) {
    Query query = new Query();
    query.addCriteria(Criteria.where("_id").is(commentId));
    mongoTemplate.findAndRemove(query, CommentEntity.class, "comment");
    return "댓글이 삭제되었습니다.";
  }
}
