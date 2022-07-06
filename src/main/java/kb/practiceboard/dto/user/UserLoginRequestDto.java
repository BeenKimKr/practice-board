package kb.practiceboard.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class UserLoginRequestDto {

  @Email(message = "이메일 형식이 올바르지 않습니다.")
  private String email;

  @Size(min = 1, message = "비밀번호를 입력해주세요.")
  private String password;


  @Builder
  public UserLoginRequestDto(String email, String password) {
    this.email = email;
    this.password = password;
  }
}
