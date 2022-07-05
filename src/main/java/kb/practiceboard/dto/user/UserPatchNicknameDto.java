package kb.practiceboard.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class UserPatchNicknameDto {

  @Size(min = 2, max = 10, message = "닉네임은 2자 이상 10자 이하로 입력해주세요.")
  private String nickname;

  @Builder
  public UserPatchNicknameDto(String nickname) {
    this.nickname = nickname;
  }
}
