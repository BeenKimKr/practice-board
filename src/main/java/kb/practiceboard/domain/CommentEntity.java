package kb.practiceboard.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "comment")
@Getter
@NoArgsConstructor
public class CommentEntity {

  @Id
  private String _id;
  private String writerId;
  private String contents;
  private String postingId;
  private String createdDateTime;
  private String updatedDateTime;

  @Builder
  public CommentEntity(String _id, String writerId, String contents, String postingId, String createdDateTime, String updatedDateTime) {
    this._id = _id;
    this.writerId = writerId;
    this.contents = contents;
    this.postingId = postingId;
    this.createdDateTime = createdDateTime;
    this.updatedDateTime = updatedDateTime;
  }
}
