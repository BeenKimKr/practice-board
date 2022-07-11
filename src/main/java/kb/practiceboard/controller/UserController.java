package kb.practiceboard.controller;

import kb.practiceboard.domain.UserEntity;
import kb.practiceboard.dto.MessageDto;
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
  public UserResponseDto registerUser(@RequestBody @Valid UserRegisterDto userRegisterDto) {
    UserEntity newUser = userService.create(userRegisterDto);
    UserResponseDto user = UserResponseDto.builder()
        .email(newUser.getEmail())
        .build();
    return user;
  }

  @PatchMapping("/user/login")
  public UserResponseDto loginUser(@RequestBody @Valid UserLoginDto userLoginDto) {
    UserEntity loginUser = userService.login(userLoginDto);
    UserResponseDto user = UserResponseDto.builder()
        .userId(loginUser.getUserId())
        .nickname(loginUser.getNickname())
        .updatePasswordRequired(loginUser.getUpdatePasswordRequired())
        .build();
    return user;
  }

  @GetMapping("/user")
  public UserResponseDto myAccount(@RequestBody UserIdDto userIdDto) {
    UserEntity myUser = userService.findUserByUserId(userIdDto.getUserId());
    UserResponseDto user = UserResponseDto.builder()
        .nickname(myUser.getNickname())
        .updatePasswordRequired(myUser.getUpdatePasswordRequired())
        .build();
    return user;
  }

  @PatchMapping("/user/nickname")
  public UserResponseDto updateNickname(@RequestBody @Valid UserPatchNicknameDto userNicknameDto) {
    userService.updateNickName(userNicknameDto);
    UserResponseDto user = UserResponseDto.builder()
        .nickname(userService.findUserByUserId(userNicknameDto.getUserId()).getNickname())
        .build();
    return user;
  }

  @PatchMapping("/user/pwd")
  public MessageDto updatePasswordUser(@RequestBody @Valid UserPatchPasswordDto userPasswordDto) {
    return MessageDto.builder().message(userService.updatePassword(userPasswordDto)).build();
  }

  @DeleteMapping("/user")
  public MessageDto deleteUser(@RequestBody UserIdDto userIdDto) {
    return MessageDto.builder().message(userService.delete(userIdDto.getUserId())).build();
  }
}
