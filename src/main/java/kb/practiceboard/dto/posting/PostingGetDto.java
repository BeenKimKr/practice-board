package kb.practiceboard.dto.posting;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.util.List;

@Getter
@NoArgsConstructor
public class PostingGetDto {


  @Size(min = 2, max = 16, message = "제목은 2자 이상 16자 이하로 입력 가능합니다.")
  private String title;

  @Size(min = 2, max = 100, message = "내용은 2자 이상 100자 이하로 입력 가능합니다.")
  private String contents;

  private String authorId;

  private String updatedDateTime;

  private List<String> fileId;

  @Builder
  public PostingGetDto(String title, String contents, String authorId, String updatedDateTime, List<String> fileId) {
    this.title = title;
    this.contents = contents;
    this.authorId = authorId;
    this.updatedDateTime = updatedDateTime;
    this.fileId = fileId;
  }
}
