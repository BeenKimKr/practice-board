package kb.practiceboard.controller;

import kb.practiceboard.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/board")
public class BoardController {

  private BoardService boardService;

  @Autowired
  public void setBoardService(BoardService boardService) {
    this.boardService = boardService;
  }


}