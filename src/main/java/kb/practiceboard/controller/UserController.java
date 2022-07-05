package kb.practiceboard.controller;

import kb.practiceboard.domain.UserEntity;
import kb.practiceboard.dto.user.UserLoginDto;
import kb.practiceboard.dto.user.UserPatchNicknameDto;
import kb.practiceboard.dto.user.UserPatchPasswordDto;
import kb.practiceboard.dto.user.UserRegisterDto;
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
        .nickname(loginUser.getNickname())
        .build();
    return user;
  }

  @GetMapping("/user")
  public String myAccount(@RequestBody String userId) {
    return userService.findUserByUserId(userId).getNickname();
  }

  @PatchMapping("/user/nickname")
  public UserPatchNicknameDto updateUserName(@RequestBody @Valid UserPatchNicknameDto userNicknameDto,
                                             @RequestParam String userId) {
    userService.updateNickName(userId, userNicknameDto);
    UserPatchNicknameDto user = UserPatchNicknameDto.builder()
        .nickname(userService.findUserByUserId(userId).getNickname())
        .build();
    return user;
  }

  @PatchMapping("/user/pwd")
  public String updatePasswordUser(@RequestBody @Valid UserPatchPasswordDto userPasswordDto,
                                   @RequestParam String userId) {
    return userService.updatePassword(userId, userPasswordDto);
  }

  @DeleteMapping("/user")
  public String deleteUser(@RequestBody String userId) {
    return userService.delete(userId);
  }
}
