package kb.practiceboard.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "posting")
@Getter
@NoArgsConstructor
public class PostingEntity {

  @Id
  private ObjectId _id;
  private String title;
  private String contents;
  private String authorId;
  private String createdDateTime;
  private String updatedDateTime;
  private String boardId;
  private List<String> fileId;

  @Builder
  public PostingEntity(ObjectId _id, String title, String contents, String authorId, String createdDateTime, String updatedDateTime, String boardId, List<String> fileId) {
    this._id = _id;
    this.title = title;
    this.contents = contents;
    this.authorId = authorId;
    this.createdDateTime = createdDateTime;
    this.updatedDateTime = updatedDateTime;
    this.boardId = boardId;
    this.fileId = fileId;
  }
}
