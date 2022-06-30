package kb.practiceboard.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Document(collection = "board")
@Getter
@NoArgsConstructor
public class Board {

  @Id
  private ObjectId _id;
  private String boardName;
  private ArrayList<String> tag;
  private String lastPostingDateTime;

  @Builder
  public Board(ObjectId _id, String boardName, ArrayList<String> tag, String lastPostingDateTime) {
    this._id = _id;
    this.boardName = boardName;
    this.tag = tag;
    this.lastPostingDateTime = lastPostingDateTime;
  }
}
