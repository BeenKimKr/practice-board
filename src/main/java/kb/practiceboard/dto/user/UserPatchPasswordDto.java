package kb.practiceboard.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
public class UserPatchPasswordDto {

  @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$",
      message = "비밀번호는 영문과 특수문자를 포함해 8자 이상 20자 이하여야 합니다.")
  private String password;

  @Builder
  public UserPatchPasswordDto(String password) {
    this.password = password;
  }
}
