package kb.practiceboard.service;

import kb.practiceboard.domain.BoardEntity;
import kb.practiceboard.dto.board.BoardDto;
import kb.practiceboard.dto.board.BoardTagDto;
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
public class BoardService {

  private MongoTemplate mongoTemplate;

  @Autowired
  public void setMongoTemplate(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  public List<BoardEntity> findAll() {
    return mongoTemplate.findAll(BoardEntity.class, "board");
  }

  public BoardEntity findById(String boardId) {
    Query query = new Query();
    query.addCriteria(Criteria.where("_id").is(boardId));

    return mongoTemplate.findOne(query, BoardEntity.class, "board");
  }

  public List<BoardEntity> findByName(String keyword) {
    Query query = new Query();
    query.addCriteria(Criteria.where("boardName").regex(keyword));

    return mongoTemplate.find(query, BoardEntity.class, "board");
  }

  public List<BoardEntity> findByTag(String keyword) {
    Query query = new Query();
    query.addCriteria(Criteria.where("tag").regex(keyword));

    return mongoTemplate.find(query, BoardEntity.class, "board");
  }

  @Transactional
  public BoardEntity createBoard(BoardDto boardCreateDto) {
    String currentDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    BoardEntity newBoard = BoardEntity.builder()
        .boardName(boardCreateDto.getBoardName() + "게시판")
        .tag(boardCreateDto.getTag())
        .lastPostingDateTime(currentDateTime)
        .build();

    return mongoTemplate.insert(newBoard, "board");
  }

  @Transactional
  public void updateLastPostingDateTime(String boardId, String updatedDateTime) {
    Query query = new Query();
    query.addCriteria(Criteria.where("_id").is(boardId));

    Update update = new Update();
    update.set("lastUpdatedDateTime", updatedDateTime);

    mongoTemplate.updateFirst(query, update, BoardEntity.class, "board");
  }

  @Transactional
  public String updateTag(String boardId, BoardTagDto boardTagDto) {
    Query query = new Query();
    query.addCriteria(Criteria.where("boardName").is(boardId));

    Update update = new Update();
    update.set("tag", boardTagDto.getTag());

    mongoTemplate.updateFirst(query, update, BoardEntity.class, "board");
    return "태그 수정이 완료되었습니다.";
  }
}
