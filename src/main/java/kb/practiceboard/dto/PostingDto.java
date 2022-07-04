package kb.practiceboard.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class PostingDto {

  @Size(min = 2, max = 16, message = "제목은 2자 이상 16자 이하로 입력 가능합니다.")
  private String title;

  @Size(min = 2, max = 16, message = "제목은 2자 이상 16자 이하로 입력 가능합니다.")
  private String contents;

  private String authorId;

  private String boardId;

  @Builder
  public PostingDto(String title, String contents, String authorId, String boardId) {
    this.title = title;
    this.contents = contents;
    this.authorId = authorId;
    this.boardId = boardId;
  }
}