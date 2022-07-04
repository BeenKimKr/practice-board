package kb.practiceboard.controller;

import kb.practiceboard.domain.BoardEntity;
import kb.practiceboard.dto.BoardDto;
import kb.practiceboard.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class BoardController {

  private BoardService boardService;

  @Autowired
  public void BoardController(BoardService boardService) {
    this.boardService = boardService;
  }

  @PostMapping("/board")
  public BoardEntity createBoard(@RequestBody @Valid BoardDto boardDto) {
    return boardService.createBoard(boardDto);
  }

  @GetMapping("/boards")
  public List<BoardEntity> allBoardList() {
    return boardService.findAll();
  }

  @PatchMapping("/board")
  public String updateTag(BoardDto boardDto) {
    return boardService.updateTag(boardDto);
  }

  @GetMapping("/boards/{criterion}/{keyword}")
  public List<BoardEntity> listByKeyword(@PathVariable String criterion,
                                         @PathVariable String keyword) {
    if (criterion.equals("boardName")) {
      return boardService.findByName(keyword);
    } else {
      return boardService.findByTag(keyword);
    }
  }
}