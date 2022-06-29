package kb.practiceboard.service;

import kb.practiceboard.domain.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

  private MongoTemplate mongoTemplate;

  @Autowired
  public void setMongoTemplate(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  public List<Board> getBoardList() {
    return mongoTemplate.findAll(Board.class, "board");
  }

  public List<Board> getBoardListByBoardName(String keyword) {
    Query query = new Query();
    query.addCriteria(Criteria.where("boardName").regex(keyword));
    return mongoTemplate.find(query, Board.class, "board");
  }


  public Board createBoard(Board board) {
    return mongoTemplate.insert(board);
  }
}
