package kb.practiceboard.dto.board;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.util.List;

@Getter
@NoArgsConstructor
public class BoardDto {

  @Size(min = 1, max = 8, message = "게시판명은 8자 이하로 입력해주세요.")
  private String boardName;

  @Size(max = 5, message = "태그는 5개 이하로 입력해주세요.")
  private List<String> tag;

  private String lastPostingDateTime;

  @Builder
  public BoardDto(String boardName, List<String> tag, String lastPostingDateTime) {
    this.boardName = boardName;
    this.tag = tag;
    this.lastPostingDateTime = lastPostingDateTime;
  }
}
