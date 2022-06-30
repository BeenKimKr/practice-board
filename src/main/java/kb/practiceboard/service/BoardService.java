package kb.practiceboard.service;

import kb.practiceboard.domain.Board;
import kb.practiceboard.domain.BoardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class BoardService {

  private MongoTemplate mongoTemplate;

  @Autowired
  public void setMongoTemplate(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  public Board createBoard(BoardDto boardDto) {
    String currentDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    Board newBoard = Board.builder()
        .boardName(boardDto.getBoardName())
        .tag(boardDto.getTag())
        .lastPostingDateTime(currentDateTime)
        .build();

    return mongoTemplate.insert(newBoard, "board");
  }
}
