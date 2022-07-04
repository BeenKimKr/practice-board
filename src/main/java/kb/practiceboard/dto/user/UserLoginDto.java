package kb.practiceboard.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;

@Getter
@NoArgsConstructor
public class UserLoginDto {

  @Email(message = "이메일 형식이 올바르지 않습니다.")
  private String email;

  @Min(value = 1, message = "비밀번호를 입력해주세요.")
  private String password;


  public UserLoginDto(String email, String password) {
    this.email = email;
    this.password = password;
  }
}
