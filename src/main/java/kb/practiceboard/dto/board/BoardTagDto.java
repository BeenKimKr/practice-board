package kb.practiceboard.dto.board;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.util.List;

@Getter
@NoArgsConstructor
public class BoardTagDto {

  @Size(max = 5, message = "태그는 5개 이하로 입력해주세요.")
  private List<String> tag;

  @Builder
  public BoardTagDto(List<String> tag) {
    this.tag = tag;
  }
}
