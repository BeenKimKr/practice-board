package kb.practiceboard.controller;

import kb.practiceboard.domain.Posting;
import kb.practiceboard.domain.PostingDto;
import kb.practiceboard.service.CommentService;
import kb.practiceboard.service.PostingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
  public Posting createPosting(@RequestBody PostingDto postingDto) {
    return postingService.create(postingDto);
  }

  @GetMapping("/posting/{_id}")
  public Posting viewPosting(@PathVariable String _id) {
    return postingService.findById(_id);
  }

  @PatchMapping("/posting/{_id}")
  public String editPosting(@PathVariable String _id,
                            @RequestBody PostingDto postingDto) {
    return postingService.update(_id, postingDto);
  }

  @GetMapping("/postings")
  public List<Posting> listAllPosting() {
    return postingService.findAll();
  }

  @GetMapping("/postings/{criterion}/{keyword}")
  public List<Posting> listByKeyword(@PathVariable String criterion,
                                     @PathVariable String keyword) {
    if (criterion == "author") {
      return postingService.findByAuthor(keyword);
    } else if (criterion == "title") {
      return postingService.findByTitle(keyword);
    } else {
      return postingService.findByContents(keyword);
    }
  }

  @DeleteMapping("/posting/{_id}")
  public String deleteByIdPosting(@PathVariable String _id) {
    return postingService.deleteOne(_id);
  }
}