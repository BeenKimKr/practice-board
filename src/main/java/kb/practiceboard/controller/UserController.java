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
  public UserRegisterDto registerUser(@RequestBody @Valid UserRegisterDto userRegisterDto) {
    UserEntity newUser = userService.create(userRegisterDto);
    UserRegisterDto user = UserRegisterDto.builder()
        .email(newUser.getEmail())
        .build();
    return user;
  }

  @PatchMapping("/user/login")
  public UserLoginDto loginUser(@RequestBody @Valid UserLoginDto userLoginDto) {
    UserEntity loginUser = userService.login(userLoginDto);
    UserLoginDto user = UserLoginDto.builder()
        .userId(loginUser.getUserId())
        .nickname(loginUser.getNickname())
        .updatePasswordRequired(loginUser.getUpdatePasswordRequired())
        .build();
    return user;
  }

  @GetMapping("/user")
  public UserResponseDto myAccount(@RequestBody String userId) {
    return UserResponseDto.builder().nickname(userService.findUserByUserId(userId).getNickname()).build();
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
  public MessageDto updatePasswordUser(@RequestBody @Valid UserPatchPasswordDto userPasswordDto) {
    return MessageDto.builder().message(userService.updatePassword(userPasswordDto)).build();
  }

  @DeleteMapping("/user")
  public MessageDto deleteUser(@RequestBody String userId) {
    return MessageDto.builder().message(userService.delete(userId)).build();
  }
}
