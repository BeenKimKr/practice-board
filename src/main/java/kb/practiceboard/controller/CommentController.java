package kb.practiceboard.controller;

import kb.practiceboard.domain.Comment;
import kb.practiceboard.domain.CommentDto;
import kb.practiceboard.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CommentController {
  private CommentService commentService;

  @Autowired
  public CommentController(CommentService commentService) {
    this.commentService = commentService;
  }

  @PostMapping("/comment")
  public Comment createComment(@RequestBody @Valid CommentDto commentDto) {
    return commentService.create(commentDto);
  }

  @PatchMapping("/comment/{commentId}")
  public String modifyComment(@PathVariable String commentId,
                              @RequestBody @Valid CommentDto commentDto) {
    return commentService.update(commentId, commentDto);
  }

  @DeleteMapping("/comment/{commentId}")
  public String deleteComment(@PathVariable String commentId) {
    return commentService.delete(commentId);
  }

  @GetMapping("/comments/{postingId}")
  public List<Comment> viewComment(@PathVariable String postingId) {
    return commentService.findByPostingId(postingId);
  }

  @GetMapping("/comments/my/{userId}")
  public List<Comment> commentListByUserId(@PathVariable String userId) {
    return commentService.findByWriterId(userId);
  }
}
