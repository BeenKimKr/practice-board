package kb.practiceboard.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@NoArgsConstructor
public class CommentDto {

  private String writerId;

  @Min(value = 1, message = "댓글 내용을 입력해주세요.")
  @Max(value = 100, message = "100자 이하로만 작성 가능합니다.")
  private String contents;

  private String postingId;

  @Builder
  public CommentDto(String writerId, String contents, String postingId) {
    this.writerId = writerId;
    this.contents = contents;
    this.postingId = postingId;
  }
}