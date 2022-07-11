package kb.practiceboard.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserIdDto {

  private String userId;

  @Builder
  public UserIdDto(String userId) {
    this.userId = userId;
  }
}