package kb.practiceboard.controller;

import com.mongodb.client.result.UpdateResult;
import kb.practiceboard.domain.User;
import kb.practiceboard.domain.UserDto;
import kb.practiceboard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UserController {

  private UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/register")
  public String registerUser(@Valid @RequestBody UserDto userDto,
                             BindingResult bindingResult,
                             Model model) {
    if (bindingResult.hasErrors()) {
      bindingResult.getAllErrors()
          .forEach(objectError -> {
            System.err.println("code : " + objectError.getCode() + "\n"
                + "defaultMessage : " + objectError.getDefaultMessage() + "\n"
                + "objectName : " + objectError.getObjectName());
          });
      model.addAttribute("message", "잘못된 형식의 회원 정보입니다.");
    } else {
      User newUser = userService.create(userDto);
      model.addAttribute("user", newUser);
    }
    return "register";
  }

  @PostMapping("/login")
  public String loginUser(@Valid @RequestBody UserDto userDto,
                          Model model,
                          BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      bindingResult.getAllErrors()
          .forEach(objectError -> {
            System.err.println("code : " + objectError.getCode() + "\n"
                + "defaultMessage : " + objectError.getDefaultMessage() + "\n"
                + "objectName : " + objectError.getObjectName());
          });
      model.addAttribute("message", "잘못된 형식의 회원 정보입니다.");
    } else {
      User logindUser = userService.login(userDto);
      model.addAttribute("user", logindUser);
    }
    return "login";
  }

  @GetMapping("/my-account")
  public String myAccount(UserDto userDto,
                          Model model) {
    User user = userService.getUserInfo(userDto);
    model.addAttribute("user", user);
    return "my-account";
  }

  @PutMapping("/my-account/{userId}/nickname")
  public String updateUserName(@PathVariable String userId,
                               @RequestBody @Valid UserDto toUpdateUserDto,
                               Model model,
                               BindingResult bindingResult) {

    if (bindingResult.hasErrors()) {
      bindingResult.getAllErrors()
          .forEach(objectError -> {
            System.err.println("code : " + objectError.getCode() + "\n"
                + "defaultMessage : " + objectError.getDefaultMessage() + "\n"
                + "objectName : " + objectError.getObjectName());
          });
      model.addAttribute("message", "닉네임 변경에 실패하였습니다.");
    } else {
      UpdateResult updatedResult = userService.updateNickName(userId, toUpdateUserDto);
      model.addAttribute("message", "닉네임 변경이 완료되었습니다.");
      model.addAttribute("updateResult", updatedResult);
    }
    return String.format("my-account/%s/nickname", userId);
  }

  @PutMapping("/my-account/{userId}/pwd")
  public String updatePasswordUser(@PathVariable String userId,
                                   @RequestBody @Valid UserDto toUpdateUserDto,
                                   Model model,
                                   BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      bindingResult.getAllErrors()
          .forEach(objectError -> {
            System.err.println("code : " + objectError.getCode() + "\n"
                + "defaultMessage : " + objectError.getDefaultMessage() + "\n"
                + "objectName : " + objectError.getObjectName());
          });
      model.addAttribute("message", "비밀번호 변경에 실패하였습니다.");
    } else {
      UpdateResult updatedResult = userService.updatePassword(userId, toUpdateUserDto);
      model.addAttribute("message", "비밀번호 변경이 완료되었습니다.");
      model.addAttribute(updatedResult);
    }
    return String.format("my-account/%s/pwd", userId);
  }

  @DeleteMapping("/my-account/deletion")
  public String deleteUser(@RequestBody String userId,
                           Model model) {
    String removeStatus = userService.delete(userId);
    model.addAttribute("message", removeStatus);
    return "my-account/deletion";
  }
}
