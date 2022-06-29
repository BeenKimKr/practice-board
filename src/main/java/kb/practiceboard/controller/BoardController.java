package kb.practiceboard.controller;

import kb.practiceboard.domain.Board;
import kb.practiceboard.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/board")
public class BoardController {

  private BoardService boardService;

  @Autowired
  public void setBoardService(BoardService boardService) {
    this.boardService = boardService;
  }

  @GetMapping("/list/{boardName}")
  public List<Board> listByBoardName(Model model, @PathVariable String boardName) {
    List<Board> board = boardService.getBoardListByBoardName(boardName);
    model.addAttribute("board", board);
    return board;
  }

  @GetMapping("/list")
  public List<Board> listAll(Model model) {
    List<Board> board = boardService.getBoardList();
    model.addAttribute("board", board);
    return board;
  }
}