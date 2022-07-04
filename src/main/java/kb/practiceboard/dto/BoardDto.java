package kb.practiceboard.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@Getter
@NoArgsConstructor
public class BoardDto {

  @Min(value = 1, message = "게시판명을 입력해주세요.")
  @Max(value = 8, message = "게시판명은 8자 이하로 입력 가능합니다.")
  private String boardName;

  @Min(value = 1, message = "태그는 최소 1개 이상 입력해주셔야 합니다.")
  @Max(value = 5, message = "태그는 최대 5개까지 입력 가능합니다.")
  private List<String> tag;

  @Builder
  public BoardDto(String boardName, List<String> tag) {
    this.boardName = boardName;
    this.tag = tag;
  }
}
