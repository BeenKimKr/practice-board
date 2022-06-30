package kb.practiceboard.controller;

import com.mongodb.client.result.UpdateResult;
import kb.practiceboard.domain.Posting;
import kb.practiceboard.domain.PostingDto;
import kb.practiceboard.service.PostingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostingController {

  private PostingService postingService;

  @Autowired
  public PostingController(PostingService postingService) {
    this.postingService = postingService;
  }

  @PostMapping("/posting/creation")
  public String createPosting(@RequestBody PostingDto postingDto,
                              @RequestBody String boardId,
                              @RequestBody String boardName,
                              Model model) {
    Posting newPosting = postingService.create(postingDto, boardId, boardName);
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
    UpdateResult updateResult = postingService.update(_id, postingDto);
    model.addAttribute("message", "게시글이 수정되었습니다.");
    return String.format("posting/%s/edit", _id);
  }

  @GetMapping("/posting/list")
  public String listAllPosting(Model model) {
    List<Posting> postingList = postingService.findAll();
    model.addAttribute("posting", postingList);
    return "posting/list";
  }

  @GetMapping("/posting/{author}/list")
  public String listByAuthorPosting(@PathVariable String author,
                                    Model model) {
    List<Posting> postingList = postingService.findByAuthor(author);
    model.addAttribute("posting", postingList);
    return String.format("posting/%s/list", author);
  }

  @GetMapping("/posting/{title}/list")
  public String listByTitlePosting(@PathVariable String title,
                                   Model model) {
    List<Posting> postingList = postingService.findByTitle(title);
    model.addAttribute("posting", postingList);
    return String.format("posting/%s/list", title);
  }

  @GetMapping("/posting/{contents}/list")
  public String listByContentsPosting(@PathVariable String contents,
                                      Model model) {
    List<Posting> postingList = postingService.findByContents(contents);
    model.addAttribute("posting", postingList);
    return String.format("posting/%s/list", contents);
  }

  @DeleteMapping("/posting/{_id}/deletion")
  public String deleteByIdPosting(@RequestBody String _id,
                                  Model model) {
    String deletionMessage = postingService.deleteOne(_id);
    model.addAttribute("message", deletionMessage);
    return String.format("posting/%s/deletion", _id);
  }
}