package kb.practiceboard.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Document(collection = "user")
@Getter
@NoArgsConstructor
public class UserDto {

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String userId;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  @NotBlank(message = "이메일 주소를 입력해주세요.")
  @Email
  private String email;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  @NotBlank(message = "이름을 입력해주세요.")
  @Size(min = 2, max = 20, message = "이름은 2자 이상 20자 이하로 입력해주세요.")
  private String userName;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  @Size(min = 2, max = 10, message = "닉네임은 2자 이상 10자 이하로 입력해주세요.")
  private String nickname;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  @NotBlank
  @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$", message = "비밀번호는 영문과 특수문자를 포함해 8자 이상 20자 이하여야 합니다.")
  private String password;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String registerDatetime;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String updatedDatetime;

  @Builder
  public UserDto(String userId, String email, String userName, String nickname, String password, String registerDatetime, String updatedDatetime) {

    this.userId = userId;
    this.email = email;
    this.userName = userName;
    this.nickname = nickname;
    this.password = password;
    this.registerDatetime = registerDatetime;
    this.updatedDatetime = updatedDatetime;
  }
}

