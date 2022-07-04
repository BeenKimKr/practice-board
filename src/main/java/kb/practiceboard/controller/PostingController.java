package kb.practiceboard.controller;

import kb.practiceboard.domain.PostingEntity;
import kb.practiceboard.dto.posting.PostingCreateDto;
import kb.practiceboard.dto.posting.PostingUpdateContentsDto;
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
  public PostingEntity createPosting(@RequestBody @Valid PostingCreateDto postingCreateDto) {
    return postingService.create(postingCreateDto);
  }

  @GetMapping("/posting/{postingId}")
  public PostingEntity viewPosting(@PathVariable String postingId) {
    return postingService.findById(postingId);
  }

  @PatchMapping("/posting/{postingId}")
  public String editPosting(@PathVariable String postingId,
                            @RequestBody @Valid PostingUpdateContentsDto postingUpdateDto) {
    return postingService.updateContents(postingId, postingUpdateDto);
  }

  @GetMapping("/postings")
  public List<PostingEntity> listAllPosting() {
    return postingService.findAll();
  }

  @GetMapping("/postings/{criterion}/{keyword}")
  public List<PostingEntity> listByKeyword(@PathVariable String criterion,
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
  public List<PostingEntity> listByBoardId(@PathVariable String boardId) {
    return postingService.findByBoardId(boardId);
  }
}