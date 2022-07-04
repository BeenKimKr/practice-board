package kb.practiceboard.controller;

import kb.practiceboard.domain.UserEntity;
import kb.practiceboard.dto.user.UserLoginDto;
import kb.practiceboard.dto.user.UserNicknameDto;
import kb.practiceboard.dto.user.UserPasswordDto;
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
  public UserEntity registerUser(@RequestBody @Valid UserRegisterDto userRegisterDto) {
    return userService.create(userRegisterDto);
  }

  @PatchMapping("/user/login")
  public UserEntity loginUser(@RequestBody @Valid UserLoginDto userLoginDto) {
    return userService.login(userLoginDto);
  }

  @GetMapping("/user/{userId}")
  public UserEntity myAccount(@PathVariable String userId) {
    return userService.findUserByUserId(userId);
  }

  @PatchMapping("/user/nickname")
  public String updateUserName(@RequestBody @Valid UserNicknameDto userNicknameDto,
                               @RequestParam String userId) {
    return userService.updateNickName(userId, userNicknameDto);
  }

  @PatchMapping("/user/pwd")
  public String updatePasswordUser(@RequestBody @Valid UserPasswordDto userPasswordDto,
                                   @RequestParam String userId) {
    return userService.updatePassword(userId, userPasswordDto);
  }

  @DeleteMapping("/user")
  public String deleteUser(@RequestBody String userId) {
    return userService.delete(userId);
  }
}
