package kb.practiceboard.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "board")
@AllArgsConstructor
@NoArgsConstructor
public class Board {

  private String boardId;
  private String boardName;
  private String tag;

  public String getBoardId() {
    return boardId;
  }

  public void setBoardId(String boardId) {
    this.boardId = boardId;
  }

  public String getBoardName() {
    return boardName;
  }

  public void setBoardName(String title) {
    this.boardName = boardName;
  }

  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }

}
