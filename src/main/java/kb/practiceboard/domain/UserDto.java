package kb.practiceboard.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Document(collection = "user")
@Getter
@NoArgsConstructor
public class UserDto {

  @Email
  private String email;

  @Size(min = 2, max = 20, message = "이름은 2자 이상 20자 이하로 입력해주세요.")
  private String userName;

  @Size(min = 2, max = 10, message = "닉네임은 2자 이상 10자 이하로 입력해주세요.")
  private String nickname;

  @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$",
      message = "비밀번호는 영문과 특수문자를 포함해 8자 이상 20자 이하여야 합니다.")
  private String password;

  @Builder
  public UserDto(String email, String userName, String nickname, String password, String registerDateTime) {
    this.email = email;
    this.userName = userName;
    this.nickname = nickname;
    this.password = password;
  }
}

