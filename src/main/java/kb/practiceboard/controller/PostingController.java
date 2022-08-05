package kb.practiceboard.controller;

import kb.practiceboard.domain.PostingEntity;
import kb.practiceboard.dto.MessageDto;
import kb.practiceboard.dto.posting.PostingCreateDto;
import kb.practiceboard.dto.posting.PostingGetDto;
import kb.practiceboard.service.CommentService;
import kb.practiceboard.service.PostingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
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
  @ResponseStatus(HttpStatus.CREATED)
  public PostingGetDto createPosting(@RequestBody @Valid PostingCreateDto postingCreateDto) {
    PostingEntity newPosting = postingService.create(postingCreateDto);
    PostingGetDto posting = PostingGetDto.builder()
        .title(newPosting.getTitle())
        .contents(newPosting.getContents())
        .authorId(newPosting.getAuthorId())
        .updatedDateTime(newPosting.getUpdatedDateTime())
        .build();
    return posting;
  }

  @GetMapping("/posting/{postingId}")
  public PostingGetDto viewPosting(@PathVariable String postingId) {
    PostingEntity selectedPosting = postingService.findById(postingId);
    PostingGetDto posting = PostingGetDto.builder()
        .title(selectedPosting.getTitle())
        .contents(selectedPosting.getContents())
        .authorId(selectedPosting.getAuthorId())
        .updatedDateTime(selectedPosting.getUpdatedDateTime())
        .fileId(selectedPosting.getFileId())
        .build();
    return posting;
  }

  @PatchMapping("/posting/{postingId}")
  public PostingGetDto editPosting(@PathVariable String postingId,
                                   @RequestBody @Valid PostingGetDto postingGetDto) {
    postingService.updateContents(postingId, postingGetDto);
    PostingEntity updatedPosting = postingService.findById(postingId);
    PostingGetDto posting = PostingGetDto.builder()
        .title(updatedPosting.getTitle())
        .contents(updatedPosting.getContents())
        .authorId(updatedPosting.getAuthorId())
        .updatedDateTime(updatedPosting.getUpdatedDateTime())
        .fileId(updatedPosting.getFileId())
        .build();
    return posting;
  }

  @GetMapping("/postings")
  public List<PostingGetDto> listAllPosting() {
    List<PostingEntity> postingList = postingService.findAll();
    List<PostingGetDto> postings = new ArrayList<PostingGetDto>();
    for (PostingEntity p : postingList) {
      postings.add(PostingGetDto.builder()
          .title(p.getTitle())
          .contents(p.getContents())
          .authorId(p.getAuthorId())
          .updatedDateTime(p.getUpdatedDateTime())
          .build());
    }
    return postings;
  }

  @GetMapping("/postings/{criterion}/{keyword}")
  public List<PostingGetDto> listByKeyword(@PathVariable String criterion,
                                           @PathVariable String keyword) {
    List<PostingEntity> postingList;
    if (criterion.equals("author")) {
      postingList = postingService.findByAuthor(keyword);
    } else if (criterion.equals("title")) {
      postingList = postingService.findByTitle(keyword);
    } else {
      postingList = postingService.findByContents(keyword);
    }

    List<PostingGetDto> postings = new ArrayList<PostingGetDto>();
    for (PostingEntity p : postingList) {
      postings.add(PostingGetDto.builder()
          .title(p.getTitle())
          .contents(p.getContents())
          .authorId(p.getAuthorId())
          .updatedDateTime(p.getUpdatedDateTime())
          .build());
    }
    return postings;
  }

  @DeleteMapping("/posting/{postingId}")
  public MessageDto deleteByIdPosting(@PathVariable String postingId) {
    MessageDto message = MessageDto.builder()
        .message(postingService.deleteOne(postingId))
        .build();
    return message;
  }

  @GetMapping("/board/{boardId}/postings")
  public List<PostingGetDto> listByBoardId(@PathVariable String boardId) {
    List<PostingEntity> postingList = postingService.findByBoardId(boardId);
    List<PostingGetDto> postings = new ArrayList<PostingGetDto>();
    for (PostingEntity p : postingList) {
      postings.add(PostingGetDto.builder()
          .title(p.getTitle())
          .contents(p.getContents())
          .authorId(p.getAuthorId())
          .updatedDateTime(p.getUpdatedDateTime())
          .build());
    }
    return postings;
  }
}