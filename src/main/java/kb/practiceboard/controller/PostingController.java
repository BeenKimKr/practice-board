package kb.practiceboard.controller;

import kb.practiceboard.domain.Comment;
import kb.practiceboard.domain.CommentDto;
import kb.practiceboard.domain.Posting;
import kb.practiceboard.domain.PostingDto;
import kb.practiceboard.service.CommentService;
import kb.practiceboard.service.PostingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
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

  @PostMapping("/posting/creation")
  public String createPosting(@RequestBody PostingDto postingDto,
                              Model model) {
    Posting newPosting = postingService.create(postingDto);
    model.addAttribute("posting", newPosting);
    return "posting/creation";
  }

  @GetMapping("/posting/{_id}")
  public String viewPosting(@PathVariable String _id,
                            Model model) {
    Posting posting = postingService.findById(_id);
    model.addAttribute("posting", _id);
    return String.format("posting/%s", _id);
  }

  @PutMapping("/posting/{_id}/edit")
  public String editPosting(@PathVariable String _id,
                            @RequestBody PostingDto postingDto,
                            Model model) {
    String message = postingService.update(_id, postingDto);
    model.addAttribute("message", message);
    return String.format("posting/%s/edit", _id);
  }

  @GetMapping("/posting/list")
  public String listAllPosting(Model model) {
    List<Posting> postingList = postingService.findAll();
    model.addAttribute("posting", postingList);
    return "posting/list";
  }

  @GetMapping("/posting/list/{criterion}/{keyword}")
  public String listByAuthorPosting(@PathVariable String criterion,
                                    @PathVariable String keyword,
                                    Model model) {
    if (criterion == "author") {
      model.addAttribute("posting", postingService.findByAuthor(keyword));
    } else if (criterion == "title") {
      model.addAttribute("posting", postingService.findByTitle(keyword));
    } else {
      model.addAttribute("posting", postingService.findByContents(keyword));
    }
    return String.format("posting/list/%s/%s", criterion, keyword);
  }

  @DeleteMapping("/posting/{_id}/deletion")
  public String deleteByIdPosting(@PathVariable String _id,
                                  Model model) {
    String deletionMessage = postingService.deleteOne(_id);
    model.addAttribute("message", deletionMessage);
    return String.format("posting/%s/deletion", _id);
  }

  @PostMapping("/posting/{postingId}/comment/creation")
  public String createComment(@PathVariable String postingId,
                              @RequestBody CommentDto commentDto,
                              Model model) {
    Comment comment = commentService.create(postingId, commentDto);
    model.addAttribute("comment", comment);
    return String.format("posting/%s/comment/creation", postingId);
  }

  @GetMapping("/posting/{postingId}/comment/list")
  public String viewComment(@PathVariable String postingId,
                            Model model) {
    List<Comment> commentList = commentService.findByPostingId(postingId);
    model.addAttribute("comment", commentList);
    return String.format("/posting/%s/comment/list", postingId);
  }

  @PutMapping("/posting/{postingId}/comment/{commentId}/edit")
  public String modifyComment(@PathVariable String postingId,
                              @PathVariable String commentId,
                              @RequestBody CommentDto commentDto,
                              Model model) {
    String message = commentService.update(commentId, commentDto);
    model.addAttribute("message", message);
    return String.format("/posting/%s/comment/%s/edit", postingId, commentId);
  }

  @DeleteMapping("/posting/{postingId}/comment/{commentId}/deletion")
  public String deleteComment(@PathVariable String postingId,
                              @PathVariable String commentId,
                              Model model) {
    String message = commentService.delete(commentId);
    model.addAttribute(message);
    return String.format("/posting/%s/comment/%s/deletion", postingId, commentId);
  }

}