package kb.practiceboard.controller;

import kb.practiceboard.domain.CommentEntity;
import kb.practiceboard.dto.comment.CommentCreateDto;
import kb.practiceboard.dto.comment.CommentUpdateDto;
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
  public CommentEntity createComment(@RequestBody @Valid CommentCreateDto commentCreateDto) {
    return commentService.create(commentCreateDto);
  }

  @PatchMapping("/comment/{commentId}")
  public String modifyComment(@PathVariable String commentId,
                              @RequestBody @Valid CommentUpdateDto commentUpdateDto) {
    return commentService.update(commentId, commentUpdateDto);
  }

  @DeleteMapping("/comment/{commentId}")
  public String deleteComment(@PathVariable String commentId) {
    return commentService.delete(commentId);
  }

  @GetMapping("/comments/{postingId}")
  public List<CommentEntity> viewComment(@PathVariable String postingId) {
    return commentService.findByPostingId(postingId);
  }

  @GetMapping("/comments/my/{userId}")
  public List<CommentEntity> commentListByUserId(@PathVariable String userId) {
    return commentService.findByWriterId(userId);
  }
}
