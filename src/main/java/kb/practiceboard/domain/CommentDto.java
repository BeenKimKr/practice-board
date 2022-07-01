package kb.practiceboard.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Document(collection = "comment")
@Getter
@NoArgsConstructor
public class CommentDto {

  @Id
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String _id;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String writerId;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  @Min(value = 1, message = "댓글 내용을 입력해주세요.")
  @Max(value = 100, message = "100자 이하로만 작성 가능합니다.")
  private String contents;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String postingId;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String createdDateTime;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String updatedDateTime;

  @Builder
  public CommentDto(String _id, String writerId, String contents, String postingId, String createdDateTime, String updatedDateTime) {
    this._id = _id;
    this.writerId = writerId;
    this.contents = contents;
    this.postingId = postingId;
    this.createdDateTime = createdDateTime;
    this.updatedDateTime = updatedDateTime;
  }
}
