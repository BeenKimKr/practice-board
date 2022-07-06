package kb.practiceboard.controller;

import kb.practiceboard.domain.UserEntity;
import kb.practiceboard.dto.user.*;
import kb.practiceboard.service.CommentService;
import kb.practiceboard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UserController {

  private UserService userService;
  private CommentService commentService;

  @Autowired
  public UserController(UserService userService, CommentService commentService) {
    this.userService = userService;
    this.commentService = commentService;
  }

  @PostMapping("/user")
  public UserRegisterDto registerUser(@RequestBody @Valid UserRegisterDto userRegisterDto) {
    UserEntity newUser = userService.create(userRegisterDto);
    UserRegisterDto user = UserRegisterDto.builder()
        .email(newUser.getEmail())
        .build();
    return user;
  }

  @PatchMapping("/user/login")
  public UserLoginResponseDto loginUser(@RequestBody @Valid UserLoginRequestDto userLoginDto) {
    UserEntity loginUser = userService.login(userLoginDto);
    UserLoginResponseDto user = UserLoginResponseDto.builder()
        .userId(loginUser.getUserId())
        .nickname(loginUser.getNickname())
        .updatePasswordRequired(loginUser.getUpdatePasswordRequired())
        .build();
    return user;
  }

  @GetMapping("/user")
  public String myaccount(@RequestBody String userId) {
    return userService.findUserByUserId(userId).getNickname();
  }

  @PatchMapping("/user/nickname")
  public UserPatchNicknameDto updateNickname(@RequestBody @Valid UserPatchNicknameDto userNicknameDto) {
    userService.updateNickName(userNicknameDto);
    UserPatchNicknameDto user = UserPatchNicknameDto.builder()
        .nickname(userService.findUserByUserId(userNicknameDto.getUserId()).getNickname())
        .build();
    return user;
  }

  @PatchMapping("/user/pwd")
  public String updatePasswordUser(@RequestBody @Valid UserPatchPasswordDto userPasswordDto) {
    return userService.updatePassword(userPasswordDto);
  }

  @DeleteMapping("/user")
  public String deleteUser(@RequestBody String userId) {
    return userService.delete(userId);
  }
}
