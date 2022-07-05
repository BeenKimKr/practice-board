package kb.practiceboard.dto.comment;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class CommentDto {

  private String writerId;

  @Size(max = 100, message = "100자 이하로만 작성 가능합니다.")
  private String contents;

  private String postingId;

  private String updatedDateTime;

  @Builder
  public CommentDto(String writerId, String contents, String postingId, String updatedDateTime) {
    this.writerId = writerId;
    this.contents = contents;
    this.postingId = postingId;
    this.updatedDateTime = updatedDateTime;
  }
}
