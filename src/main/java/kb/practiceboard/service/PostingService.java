package kb.practiceboard.service;

import com.mongodb.client.result.UpdateResult;
import kb.practiceboard.domain.Posting;
import kb.practiceboard.domain.PostingDto;
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
public class PostingService {
  private MongoTemplate mongoTemplate;

  public PostingService(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Autowired
  public void setMongoTemplate(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  public Posting findById(String _id) {
    Query query = new Query();
    query.addCriteria(Criteria.where("_id").is(_id));
    Posting posting = mongoTemplate.findOne(query, Posting.class, "posting");
    return posting;
  }

  public List<Posting> findAll() {
    List<Posting> postingList = mongoTemplate.findAll(Posting.class, "posting");
    return postingList;
  }

  public List<Posting> findByAuthor(String keyword) {
    Query query = new Query();
    query.addCriteria(Criteria.where("author").regex(keyword));
    List<Posting> postingList = mongoTemplate.find(query, Posting.class, "posting");
    return postingList;
  }

  public List<Posting> findByTitle(String keyword) {
    Query query = new Query();
    query.addCriteria(Criteria.where("title").regex(keyword));
    List<Posting> postingList = mongoTemplate.find(query, Posting.class, "posting");
    return postingList;
  }

  public List<Posting> findByContents(String keyword) {
    Query query = new Query();
    query.addCriteria(Criteria.where("contents").regex(keyword));
    List<Posting> postingList = mongoTemplate.find(query, Posting.class, "posting");
    return postingList;
  }

  public Posting create(PostingDto postingDto,
                        String boardId,
                        String boardName) {
    String currentDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    Posting posting = Posting.builder()
        ._id(postingDto.get_id())
        .title(postingDto.getTitle())
        .contents(postingDto.getContents())
        .author(postingDto.getAuthor())
        .authorId(postingDto.getAuthorId())
        .createdDateTime(currentDateTime)
        .updatedDateTime(currentDateTime)
        .boardId(boardId)
        .boardName(boardName)
        .build();

    return posting;
  }

  public UpdateResult update(String _id, PostingDto postingDto) {
    Query query = new Query();
    query.addCriteria(Criteria.where("_id").is(_id));

    Update update = new Update();
    update.set("title", postingDto.getTitle());
    update.set("contents", postingDto.getContents());

    String currentDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    update.set("updatedDateTime", currentDateTime);

    UpdateResult updateResult = mongoTemplate.updateMulti(query, update, "posting");

    return updateResult;
  }

  public String deleteOne(String _id) {
    Query query = new Query();
    query.addCriteria(Criteria.where("_id").is(_id));
    mongoTemplate.remove(query, Posting.class, "posting");
    return "게시글 삭제가 완료되었습니다.";
  }

  public void deleteAll(String authorId) {
    Query query = new Query();
    query.addCriteria(Criteria.where("authorId").is(authorId));
    mongoTemplate.findAndRemove(query, Posting.class, "posting");
  }

}
