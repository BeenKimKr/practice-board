package kb.practiceboard.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user")
@Getter
@NoArgsConstructor
public class User {

  private String userId;
  private String email;
  private String userName;
  private String nickname;
  private String password;
  private String registerDateTime;
  private String updatedDateTime;
  private Boolean updatePasswordRequired;

  @Builder
  public User(String userId, String email, String userName, String nickname, String password, String registerDateTime, String updatedDateTime, Boolean updatePasswordRequired) {
    this.userId = userId;
    this.email = email;
    this.userName = userName;
    this.nickname = nickname;
    this.password = password;
    this.registerDateTime = registerDateTime;
    this.updatedDateTime = updatedDateTime;
    this.updatePasswordRequired = updatePasswordRequired;
  }
}

