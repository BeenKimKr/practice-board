package kb.practiceboard.controller;

import kb.practiceboard.domain.Board;
import kb.practiceboard.domain.BoardDto;
import kb.practiceboard.service.BoardService;
import kb.practiceboard.service.PostingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BoardController {

  private BoardService boardService;
  private PostingService postingService;

  @Autowired
  public void setBoardService(BoardService boardSevice, PostingService postingService) {
    this.boardService = boardService;
  }

  @PostMapping("/board")
  public Board createBoard(@RequestBody BoardDto boardDto) {
    return boardService.createBoard(boardDto);
  }


  @GetMapping("/board/{criterion}/{keyword}")
  public List<Board> listByKeyword(@PathVariable String criterion,
                                   @PathVariable String keyword,
                                   Model model) {
    if (criterion == "boardName") {
      return boardService.findByName(keyword);
    } else {
      return boardService.findByTag(keyword);
    }
  }

  @GetMapping("/boards")
  public List<Board> allBoardList(Model model) {
    return boardService.findAll();
  }
}