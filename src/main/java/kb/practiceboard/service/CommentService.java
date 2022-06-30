package kb.practiceboard.service;

import com.mongodb.client.result.UpdateResult;
import kb.practiceboard.domain.Comment;
import kb.practiceboard.domain.CommentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CommentService {
  private MongoTemplate mongoTemplate;

  public CommentService(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Autowired
  public void setMongoTemplate(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  public List<Comment> findAll() {
    List<Comment> commentList = mongoTemplate.findAll(Comment.class, "comment");
    return commentList;
  }

  public List<Comment> findByWriterId(String writerId) {
    Query query = new Query();
    query.addCriteria(Criteria.where("writerId").is(writerId));
    List<Comment> commentList = mongoTemplate.find(query, Comment.class, "comment");
    return commentList;
  }

  public Comment create(CommentDto commentDto,
                        String postingId) {
    String currentDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    Comment comment = Comment.builder()
        ._id(commentDto.get_id())
        .writer(commentDto.getWriter())
        .writerId(commentDto.getWriterId())
        .contents(commentDto.getContents())
        .postingId(postingId)
        .createdDateTime(commentDto.getCreatedDateTime())
        .updatedDateTime(commentDto.getUpdatedDateTime())
        .build();

    return comment;
  }

  public UpdateResult update(String _id,
                             CommentDto commentDto) {
    Query query = new Query();
    query.addCriteria(Criteria.where("_id").is(_id));

    Update update = new Update();
    update.set("contents", commentDto.getContents());

    String currentDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    update.set("updatedDateTime", currentDateTime);

    UpdateResult updateResult = mongoTemplate.updateMulti(query, update, "comment");
    return updateResult;
  }

  public String delete(String commentId) {
    Query query = new Query();
    query.addCriteria(Criteria.where(commentId).is(commentId));
    mongoTemplate.remove(query, Comment.class, "comment");
    return "댓글이 삭제되었습니다.";
  }
}
