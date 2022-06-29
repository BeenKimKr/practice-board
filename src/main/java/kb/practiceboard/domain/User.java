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
  private String registerDatetime;
  private String updatedDatetime;

  @Builder
  public User(String userId, String email, String userName, String nickname, String password, String registerDatetime, String updatedDatetime) {
    this.userId = userId;
    this.email = email;
    this.userName = userName;
    this.nickname = nickname;
    this.password = password;
    this.registerDatetime = registerDatetime;
    this.updatedDatetime = updatedDatetime;
  }
}

