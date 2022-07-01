package kb.practiceboard.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "posting")
@Getter
@NoArgsConstructor
public class Posting {

  @Id
  private ObjectId _id;
  private String title;
  private String contents;
  private String author;
  private String authorId;
  private String createdDateTime;
  private String updatedDateTime;
  private String boardId;
  private String boardName;

  @Builder
  public Posting(ObjectId _id, String title, String contents, String author, String authorId, String createdDateTime, String updatedDateTime, String boardId, String boardName) {
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
