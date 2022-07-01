package kb.practiceboard.controller;

import kb.practiceboard.domain.User;
import kb.practiceboard.domain.UserDto;
import kb.practiceboard.service.CommentService;
import kb.practiceboard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
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
  public User registerUser(@Valid @RequestBody UserDto userDto,
                           BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      bindingResult.getAllErrors()
          .forEach(objectError -> {
            System.err.println("code : " + objectError.getCode() + "\n"
                + "defaultMessage : " + objectError.getDefaultMessage() + "\n"
                + "objectName : " + objectError.getObjectName());
          });
    }
    return userService.create(userDto);
  }

  @PostMapping("/user/login")
  public User loginUser(@Valid @RequestBody UserDto userDto,
                        BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      bindingResult.getAllErrors()
          .forEach(objectError -> {
            System.err.println("code : " + objectError.getCode() + "\n"
                + "defaultMessage : " + objectError.getDefaultMessage() + "\n"
                + "objectName : " + objectError.getObjectName());
          });
    }
    return userService.login(userDto);
  }

  @GetMapping("/user/{userId}")
  public User myAccount(@RequestBody UserDto userDto) {
    return userService.findUserByUserId(userDto);
  }

  @PatchMapping("/user/nickname")
  public String updateUserName(@RequestBody @Valid UserDto toUpdateUserDto,
                               BindingResult bindingResult) {

    if (bindingResult.hasErrors()) {
      bindingResult.getAllErrors()
          .forEach(objectError -> {
            System.err.println("code : " + objectError.getCode() + "\n"
                + "defaultMessage : " + objectError.getDefaultMessage() + "\n"
                + "objectName : " + objectError.getObjectName());
          });
    }
    return userService.updateNickName(toUpdateUserDto);
  }

  @PatchMapping("/user/pwd")
  public String updatePasswordUser(@RequestBody @Valid UserDto toUpdateUserDto,
                                   BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      bindingResult.getAllErrors()
          .forEach(objectError -> {
            System.err.println("code : " + objectError.getCode() + "\n"
                + "defaultMessage : " + objectError.getDefaultMessage() + "\n"
                + "objectName : " + objectError.getObjectName());
          });
    }
    return userService.updatePassword(toUpdateUserDto);
  }

  @DeleteMapping("/user")
  public String deleteUser(@RequestBody String userId) {
    return userService.delete(userId);
  }
}
