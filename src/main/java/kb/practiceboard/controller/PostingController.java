package kb.practiceboard.controller;

import kb.practiceboard.domain.Posting;
import kb.practiceboard.domain.PostingDto;
import kb.practiceboard.service.CommentService;
import kb.practiceboard.service.PostingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class PostingController {

  private PostingService postingService;
  private CommentService commentService;

  @Autowired
  public PostingController(PostingService postingService, CommentService commentService) {
    this.postingService = postingService;
    this.commentService = commentService;
  }

  @PostMapping("/posting")
  public Posting createPosting(@RequestBody @Valid PostingDto postingDto) {
    return postingService.create(postingDto);
  }

  @GetMapping("/posting/{postingId}")
  public Posting viewPosting(@PathVariable String postingId) {
    return postingService.findById(postingId);
  }

  @PatchMapping("/posting/{postingId}")
  public String editPosting(@PathVariable String postingId,
                            @RequestBody @Valid PostingDto postingDto) {
    return postingService.update(postingId, postingDto);
  }

  @GetMapping("/postings")
  public List<Posting> listAllPosting() {
    return postingService.findAll();
  }

  @GetMapping("/postings/{criterion}/{keyword}")
  public List<Posting> listByKeyword(@PathVariable String criterion,
                                     @PathVariable String keyword) {
    if (criterion.equals("author")) {
      return postingService.findByAuthor(keyword);
    } else if (criterion.equals("title")) {
      return postingService.findByTitle(keyword);
    } else {
      return postingService.findByContents(keyword);
    }
  }

  @DeleteMapping("/posting/{postingId}")
  public String deleteByIdPosting(@PathVariable String postingId) {
    return postingService.deleteOne(postingId);
  }

  @GetMapping("/board/{boardId}/postings")
  public List<Posting> listByBoardId(@PathVariable String boardId) {
    return postingService.findByBoardId(boardId);
  }
}