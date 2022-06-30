package kb.practiceboard.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Size;
import java.util.ArrayList;

@Document(collection = "board")
@Getter
@NoArgsConstructor
public class BoardDto {

  @Id
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private ObjectId _id;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  @Size(min = 1, message = "게시판명을 입력해주세요.")
  @Size(max = 8, message = "게시판명은 8자 이하로 입력 가능합니다.")
  private String boardName;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  @Size(min = 1, message = "태그는 최소 1개 이상 입력해주셔야 합니다.")
  @Size(max = 5, message = "태그는 최대 5개까지 입력 가능합니다.")
  private ArrayList<String> tag;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String lastPostingDateTime;

  @Builder
  public BoardDto(ObjectId _id, String boardName, ArrayList<String> tag, String lastPostingDateTime) {
    this._id = _id;
    this.boardName = boardName;
    this.tag = tag;
    this.lastPostingDateTime = lastPostingDateTime;
  }
}
