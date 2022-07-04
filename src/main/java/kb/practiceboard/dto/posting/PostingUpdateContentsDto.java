package kb.practiceboard.dto.posting;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class PostingUpdateContentsDto {

  @Size(min = 2, max = 16, message = "제목은 2자 이상 16자 이하로 입력 가능합니다.")
  private String title;

  @Size(min = 2, max = 16, message = "제목은 2자 이상 16자 이하로 입력 가능합니다.")
  private String contents;


  public PostingUpdateContentsDto(String title, String contents) {
    this.title = title;
    this.contents = contents;
  }
}
