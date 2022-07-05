package kb.practiceboard.controller;

import kb.practiceboard.domain.CommentEntity;
import kb.practiceboard.dto.comment.CommentDto;
import kb.practiceboard.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class CommentController {
  private CommentService commentService;

  @Autowired
  public CommentController(CommentService commentService) {
    this.commentService = commentService;
  }

  @PostMapping("/comment")
  public CommentDto createComment(@RequestBody @Valid CommentDto commentDto) {
    CommentEntity newComment = commentService.create(commentDto);
    CommentDto comment = CommentDto.builder()
        .writerId(newComment.getWriterId())
        .contents(newComment.getContents())
        .updatedDateTime(newComment.getUpdatedDateTime())
        .build();
    return comment;
  }

  @PatchMapping("/comment/{commentId}")
  public CommentDto modifyComment(@PathVariable String commentId,
                                  @RequestBody @Valid CommentDto commentDto) {
    commentService.update(commentId, commentDto);
    CommentEntity updatedComment = commentService.findByCommentId(commentId);
    CommentDto comment = CommentDto.builder()
        .contents(updatedComment.getContents())
        .updatedDateTime(updatedComment.getUpdatedDateTime())
        .build();
    return comment;
  }

  @DeleteMapping("/comment/{commentId}")
  public String deleteComment(@PathVariable String commentId) {
    return commentService.delete(commentId);
  }

  @GetMapping("/comments/{postingId}")
  public List<CommentDto> viewComment(@PathVariable String postingId) {
    List<CommentEntity> commentList = commentService.findByPostingId(postingId);
    List<CommentDto> comments = new ArrayList<CommentDto>();
    for (CommentEntity c : commentList) {
      comments.add(CommentDto.builder()
          .contents(c.getContents())
          .postingId(c.getPostingId())
          .updatedDateTime(c.getUpdatedDateTime())
          .build());
    }
    return comments;
  }

  @GetMapping("/comments/my/{userId}")
  public List<CommentDto> commentListByUserId(@PathVariable String userId) {
    List<CommentEntity> commentList = commentService.findByWriterId(userId);
    List<CommentDto> comments = new ArrayList<CommentDto>();
    for (CommentEntity c : commentList) {
      comments.add(CommentDto.builder()
          .contents(c.getContents())
          .postingId(c.getPostingId())
          .updatedDateTime(c.getUpdatedDateTime())
          .build());
    }
    return comments;
  }
}
