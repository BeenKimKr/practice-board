package kb.practiceboard.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "comment")
@Getter
@NoArgsConstructor
public class Comment {

  @Id
  private String _id;
  private String writer;
  private String writerId;
  private String contents;
  private String postingId;
  private String createdDateTime;
  private String updatedDateTime;

  @Builder
  public Comment(String _id, String writer, String writerId, String contents, String postingId, String createdDateTime, String updatedDateTime) {
    this._id = _id;
    this.writer = writer;
    this.writerId = writerId;
    this.contents = contents;
    this.postingId = postingId;
    this.createdDateTime = createdDateTime;
    this.updatedDateTime = updatedDateTime;
  }
}
