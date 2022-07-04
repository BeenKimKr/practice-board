package kb.practiceboard.service;

import kb.practiceboard.domain.PostingEntity;
import kb.practiceboard.dto.posting.PostingCreateDto;
import kb.practiceboard.dto.posting.PostingUpdateContentsDto;
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
public class PostingService {
  private MongoTemplate mongoTemplate;
  private BoardService boardService;
  private FileService fileService;

  public PostingService(MongoTemplate mongoTemplate, BoardService boardService, FileService fileService) {
    this.mongoTemplate = mongoTemplate;
    this.boardService = boardService;
    this.fileService = fileService;
  }

  @Autowired
  public void setMongoTemplate(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  public PostingEntity findById(String _id) {
    Query query = new Query();
    query.addCriteria(Criteria.where("_id").is(_id));
    PostingEntity posting = mongoTemplate.findOne(query, PostingEntity.class, "posting");
    return posting;
  }

  public List<PostingEntity> findAll() {
    List<PostingEntity> postingList = mongoTemplate.findAll(PostingEntity.class, "posting");
    return postingList;
  }

  public List<PostingEntity> findByAuthor(String keyword) {
    Query query = new Query();
    query.addCriteria(Criteria.where("author").regex(keyword));
    List<PostingEntity> postingList = mongoTemplate.find(query, PostingEntity.class, "posting");
    return postingList;
  }

  public List<PostingEntity> findByTitle(String keyword) {
    Query query = new Query();
    query.addCriteria(Criteria.where("title").regex(keyword));
    List<PostingEntity> postingList = mongoTemplate.find(query, PostingEntity.class, "posting");
    return postingList;
  }

  public List<PostingEntity> findByContents(String keyword) {
    Query query = new Query();
    query.addCriteria(Criteria.where("contents").regex(keyword));
    List<PostingEntity> postingList = mongoTemplate.find(query, PostingEntity.class, "posting");
    return postingList;
  }

  public List<PostingEntity> findByBoardId(String keyword) {
    Query query = new Query();
    query.addCriteria(Criteria.where("boardId").is(keyword));
    List<PostingEntity> postingList = mongoTemplate.find(query, PostingEntity.class, "posting");
    return postingList;
  }

  @Transactional
  public PostingEntity create(PostingCreateDto postingCreateDto) {
    String currentDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    PostingEntity newPosting = PostingEntity.builder()
        .title(postingCreateDto.getTitle())
        .contents(postingCreateDto.getContents())
        .authorId(postingCreateDto.getAuthorId())
        .createdDateTime(currentDateTime)
        .updatedDateTime(currentDateTime)
        .boardId(postingCreateDto.getBoardId())
        .build();

    boardService.updateLastPostingDateTime(postingCreateDto.getBoardId(), currentDateTime);
    return mongoTemplate.insert(newPosting, "posting");
  }

  @Transactional
  public String updateContents(String postingId, PostingUpdateContentsDto postingUpdateDto) {
    Query query = new Query();
    query.addCriteria(Criteria.where("_id").is(postingId));

    Update update = new Update();
    update.set("title", postingUpdateDto.getTitle());
    update.set("contents", postingUpdateDto.getContents());

    String currentDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    update.set("updatedDateTime", currentDateTime);

    mongoTemplate.updateMulti(query, update, "posting");
    return "게시글 수정이 완료되었습니다.";
  }

  @Transactional
  public void updateFiles(String postingId, String oldFileId, String newFileId) {
    List<String> files = findById(postingId).getFileId();
    files.remove(oldFileId);
    files.add(newFileId);

    Query query = new Query();
    query.addCriteria(Criteria.where("_id").regex(postingId));

    Update update = new Update();
    update.set("files", files);
    mongoTemplate.updateFirst(query, update, PostingEntity.class, "posting");
    return;
  }

  @Transactional
  public String deleteOne(String _id) {
    Query query = new Query();
    query.addCriteria(Criteria.where("_id").is(_id));
    mongoTemplate.remove(query, PostingEntity.class, "posting");
    fileService.deleteByPostingId(_id);
    return "게시글 삭제가 완료되었습니다.";
  }

  @Transactional
  public void deleteAll(String authorId) {
    Query query = new Query();
    query.addCriteria(Criteria.where("authorId").is(authorId));
    mongoTemplate.findAndRemove(query, PostingEntity.class, "posting");
  }
}
