package kb.practiceboard.controller;

import kb.practiceboard.domain.BoardEntity;
import kb.practiceboard.dto.board.BoardCreateDto;
import kb.practiceboard.dto.board.BoardTagDto;
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
  public BoardEntity createBoard(@RequestBody @Valid BoardCreateDto boardCreateDto) {
    return boardService.createBoard(boardCreateDto);
  }

  @GetMapping("/boards")
  public List<BoardEntity> allBoardList() {
    return boardService.findAll();
  }

  @PatchMapping("/board")
  public String updateTag(@RequestBody @Valid BoardTagDto boardTagDto,
                          @RequestParam String boardId) {
    return boardService.updateTag(boardId, boardTagDto);
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