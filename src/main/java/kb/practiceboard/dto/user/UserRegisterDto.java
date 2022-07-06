package kb.practiceboard.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class UserRegisterDto {

  @Email
  private String email;

  @Size(min = 2, max = 20, message = "이름은 2자 이상 20자 이하로 입력해주세요.")
  private String userName;

  @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$",
      message = "비밀번호는 영문과 특수문자를 포함해 8자 이상 20자 이하여야 합니다.")
  private String password;


  @Builder
  public UserRegisterDto(String email, String userName, String password) {
    this.email = email;
    this.userName = userName;
    this.password = password;
  }
}
