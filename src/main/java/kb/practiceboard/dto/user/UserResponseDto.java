package kb.practiceboard.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponseDto {

  private String email;
  private String userId;
  private String nickname;
  private Boolean updatePasswordRequired;


  @Builder
  public UserResponseDto(String email, String userId, String nickname, Boolean updatePasswordRequired) {
    this.email = email;
    this.userId = userId;
    this.nickname = nickname;
    this.updatePasswordRequired = updatePasswordRequired;
  }
}