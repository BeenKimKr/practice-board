package kb.practiceboard.service;

import kb.practiceboard.domain.Board;
import kb.practiceboard.domain.BoardDto;
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

  public List<Board> findAll() {
    return mongoTemplate.findAll(Board.class, "board");
  }

  public List<Board> findByName(String keyword) {
    Query query = new Query();
    query.addCriteria(Criteria.where("boardName").regex(keyword));

    return mongoTemplate.find(query, Board.class, "board");
  }

  public List<Board> findByTag(String keyword) {
    Query query = new Query();
    query.addCriteria(Criteria.where("tag").regex(keyword));

    return mongoTemplate.find(query, Board.class, "board");
  }

  @Transactional
  public Board createBoard(BoardDto boardDto) {
    String currentDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    Board newBoard = Board.builder()
        .boardName(boardDto.getBoardName() + "게시판")
        .tag(boardDto.getTag())
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

    mongoTemplate.updateFirst(query, update, Board.class, "board");
  }
}
