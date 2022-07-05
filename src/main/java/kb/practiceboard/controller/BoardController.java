package kb.practiceboard.controller;

import kb.practiceboard.domain.BoardEntity;
import kb.practiceboard.dto.board.BoardDto;
import kb.practiceboard.dto.board.BoardTagDto;
import kb.practiceboard.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
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
  public BoardDto createBoard(@RequestBody @Valid BoardDto boardCreateDto) {
    BoardEntity newBoard = boardService.createBoard(boardCreateDto);
    BoardDto board = BoardDto.builder()
        .boardName(newBoard.getBoardName())
        .tag(newBoard.getTag())
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
          .build());
    }
    return boards;
  }

  @PatchMapping("/board")
  public BoardTagDto updateTag(@RequestBody @Valid BoardTagDto boardTagDto,
                               @RequestParam String boardId) {
    boardService.updateTag(boardId, boardTagDto);
    BoardEntity boardList = boardService.findById(boardId);
    BoardTagDto board = BoardTagDto.builder()
        .tag(boardList.getTag())
        .build();
    return board;
  }

  @GetMapping("/boards/{criterion}/{keyword}")
  public List<BoardDto> listByKeyword(@PathVariable String criterion,
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
          .build());
    }
    return boards;
  }
}