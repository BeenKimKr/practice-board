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

  /*
   * Setter Entity class 내에서 사용하게 되면, instance 값들이 언제 어디서 변하는지 명확하게 알 수 없다.
   * 따라서 되도록 Builder 패턴을 사용해서 어떤 값을 어떤 필드에 넣는지 확인 -> Setter를 대체함.
   */
}
