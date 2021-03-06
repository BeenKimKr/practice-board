package kb.practiceboard.dto.posting;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class PostingCreateDto {

  @Size(min = 2, max = 16, message = "제목은 2자 이상 16자 이하로 입력 가능합니다.")
  private String title;

  @Size(min = 2, max = 100, message = "내용은 2자 이상 100자 이하로 입력 가능합니다.")
  private String contents;

  private String authorId;

  private String boardId;

  @Builder
  public PostingCreateDto(String title, String contents, String authorId, String boardId) {
    this.title = title;
    this.contents = contents;
    this.authorId = authorId;
    this.boardId = boardId;
  }
}
