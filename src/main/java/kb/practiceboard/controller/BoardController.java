package kb.practiceboard.controller;

import kb.practiceboard.domain.BoardEntity;
import kb.practiceboard.dto.board.BoardCreateDto;
import kb.practiceboard.dto.board.BoardDto;
import kb.practiceboard.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class BoardController {

  private BoardService boardService;

  @Autowired
  public void BoardController(BoardService boardService) {
    this.boardService = boardService;
  }

  @PostMapping("/board")
  @ResponseStatus(code = HttpStatus.CREATED)
  public BoardDto createBoard(@RequestBody @Valid BoardCreateDto boardCreateDto) {
    BoardEntity newBoard = boardService.createBoard(boardCreateDto);
    BoardDto board = BoardDto.builder()
        .boardName(newBoard.getBoardName())
        .tag(newBoard.getTag())
        .lastPostingDateTime(newBoard.getLastPostingDateTime())
        .build();
    return board;
  }

  @GetMapping("/boards")
  public List<BoardDto> allBoardList() {
    List<BoardEntity> boardList = boardService.findAll();
    List<BoardDto> boards = new ArrayList<BoardDto>();
    for (BoardEntity b : boardList) {
      boards.add(BoardDto.builder()
          .boardName(b.getBoardName())
          .tag(b.getTag())
          .lastPostingDateTime(b.getLastPostingDateTime())
          .build());
    }
    return boards;
  }

  @PatchMapping("/board")
  public BoardDto updateTag(@RequestBody @Valid BoardDto boardDto) {
    boardService.updateTag(boardDto);
    BoardDto board = BoardDto.builder()
        .tag(boardService.findOneByName(boardDto.getBoardName()).getTag())
        .build();
    return board;
  }

  @GetMapping("/boards/{criterion}/{keyword}")
  public List<BoardDto> listByKeywords(@PathVariable String criterion,
                                       @PathVariable String keyword) {
    List<BoardEntity> boardList;
    if (criterion.equals("boardName")) {
      boardList = boardService.findByName(keyword);
    } else {
      boardList = boardService.findByTag(keyword);
    }

    List<BoardDto> boards = new ArrayList<BoardDto>();
    for (BoardEntity b : boardList) {
      boards.add(BoardDto.builder()
          .boardName(b.getBoardName())
          .tag(b.getTag())
          .lastPostingDateTime(b.getLastPostingDateTime())
          .build());
    }
    return boards;
  }
}