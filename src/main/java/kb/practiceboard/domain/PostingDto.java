package kb.practiceboard.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Size;

@Document(collection = "posting")
@Getter
@NoArgsConstructor
public class PostingDto {

  @Id
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private ObjectId _id;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  @Size(min = 2, max = 16, message = "제목은 2자 이상 16자 이하로 입력 가능합니다.")
  private String title;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  @Size(min = 2, max = 16, message = "제목은 2자 이상 16자 이하로 입력 가능합니다.")
  private String contents;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String author;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String authorId;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String createdDateTime;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String updatedDateTime;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String boardId;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String boardName;

  @Builder
  public PostingDto(ObjectId _id, String title, String contents, String author, String authorId, String createdDateTime, String updatedDateTime, String boardId, String boardName) {
    this._id = _id;
    this.title = title;
    this.contents = contents;
    this.author = author;
    this.authorId = authorId;
    this.createdDateTime = createdDateTime;
    this.updatedDateTime = updatedDateTime;
    this.boardId = boardId;
    this.boardName = boardName;
  }
}