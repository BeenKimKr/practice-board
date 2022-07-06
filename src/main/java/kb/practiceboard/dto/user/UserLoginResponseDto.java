package kb.practiceboard.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserLoginResponseDto {

  private String userId;
  private String nickname;
  private Boolean updatePasswordRequired;

  @Builder
  public UserLoginResponseDto(String userId, String nickname, Boolean updatePasswordRequired) {
    this.userId = userId;
    this.nickname = nickname;
    this.updatePasswordRequired = updatePasswordRequired;
  }
}
