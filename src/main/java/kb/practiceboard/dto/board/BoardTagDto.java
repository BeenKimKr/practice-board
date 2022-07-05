package kb.practiceboard.dto.board;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@Getter
@NoArgsConstructor
public class BoardTagDto {

  @Min(value = 1, message = "태그는 최소 1개 이상 입력해주셔야 합니다.")
  @Max(value = 5, message = "태그는 최대 5개까지 입력 가능합니다.")
  private List<String> tag;

  @Builder
  public BoardTagDto(List<String> tag) {
    this.tag = tag;
  }
}
