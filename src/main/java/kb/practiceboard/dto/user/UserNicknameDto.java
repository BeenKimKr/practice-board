package kb.practiceboard.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class UserNicknameDto {

  @Size(min = 2, max = 10, message = "닉네임은 2자 이상 10자 이하로 입력해주세요.")
  private String nickname;


  public UserNicknameDto(String nickname) {
    this.nickname = nickname;
  }
}
