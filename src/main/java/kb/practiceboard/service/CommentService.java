package kb.practiceboard.service;

import kb.practiceboard.domain.Comment;
import kb.practiceboard.domain.CommentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

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

  public List<Comment> findByPostingId(String postingId) {
    Query query = new Query();
    query.addCriteria(Criteria.where("_id").is(postingId));
    List<Comment> commentList = mongoTemplate.find(query, Comment.class, "comment");
    return commentList;
  }

  public List<Comment> findByWriterId(String writerId) {
    Query query = new Query();
    query.addCriteria(Criteria.where("writerId").is(writerId));
    List<Comment> commentList = mongoTemplate.find(query, Comment.class, "comment");
    return commentList;
  }

  public Comment create(String postingId,
                        CommentDto commentDto) {
    String currentDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    Comment comment = Comment.builder()
        .writer(commentDto.getWriter())
        .writerId(commentDto.getWriterId())
        .contents(commentDto.getContents())
        .postingId(postingId)
        .createdDateTime(currentDateTime)
        .updatedDateTime(currentDateTime)
        .build();

    return mongoTemplate.insert(comment, "comment");
  }

  public String update(String _id,
                       CommentDto commentDto) {
    Query query = new Query();
    query.addCriteria(Criteria.where("_id").is(_id));

    Update update = new Update();
    update.set("contents", commentDto.getContents());

    String currentDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    update.set("updatedDateTime", currentDateTime);

    mongoTemplate.updateMulti(query, update, "comment");
    return "댓글이 수정되었습니다.";
  }

  public String delete(String commentId) {
    Query query = new Query();
    query.addCriteria(Criteria.where("_id").is(commentId));
    mongoTemplate.findAndRemove(query, Comment.class, "comment");
    return "댓글이 삭제되었습니다.";
  }
}
