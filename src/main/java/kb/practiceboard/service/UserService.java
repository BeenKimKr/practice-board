package kb.practiceboard.service;

import com.mongodb.client.result.UpdateResult;
import kb.practiceboard.domain.User;
import kb.practiceboard.domain.UserDto;
import kb.practiceboard.handler.AlreadyExistEmailException;
import kb.practiceboard.handler.CannotFindEmailException;
import kb.practiceboard.handler.WrongPasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

  private MongoTemplate mongoTemplate;
  private PasswordEncoder passwordEncoder;

  @Autowired
  public UserService(MongoTemplate mongoTemplate, PasswordEncoder passwordEncoder) {
    this.mongoTemplate = mongoTemplate;
    this.passwordEncoder = passwordEncoder;
  }

  @Autowired
  public void setMongoTemplate(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  // email 중복 체크
  @Transactional
  public User create(@Valid UserDto userDto) {
    String email = userDto.getEmail();
    Optional<User> user = Optional.ofNullable(findByEmail(email));
    if (!user.isEmpty()) {
      throw new AlreadyExistEmailException();
    }

    String uuid = UUID.randomUUID().toString();
    String encodedPassword = passwordEncoder.encode(userDto.getPassword());
    String datetime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    User newUser = User.builder()
        .userId(uuid)
        .email(userDto.getEmail())
        .userName(userDto.getUserName())
        .password(encodedPassword)
        .registerDatetime(datetime)
        .updatedDatetime(datetime)
        .build();

    return mongoTemplate.insert(newUser, "user");
  }

  public User findByUserId(String id) {
    User user = mongoTemplate.findById(id, User.class, "user");
    return user;
  }

  public User findByEmail(String email) {
    Query query = new Query();
    query.addCriteria(Criteria.where("email").is(email));
    User user = mongoTemplate.findOne(query, User.class, "user");
    return user;
  }

  public User findByUserName(String userName) {
    Query query = new Query();
    query.addCriteria(Criteria.where("userName").is(userName));
    User user = mongoTemplate.findOne(query, User.class, "user");
    return user;
  }

  // SecurityContextHolder
  public User login(UserDto userDto) {
    String email = userDto.getEmail();
    Optional<User> user = Optional.ofNullable(findByEmail(email));
    if (user.isEmpty()) {
      throw new CannotFindEmailException();
    }

    User loginUser = findByEmail(userDto.getEmail());
    if (passwordEncoder.matches(userDto.getPassword(), loginUser.getPassword())) {
      throw new WrongPasswordException();
    }

    // 로그인시 현재 DateTime - updatedDatetime = 3개월이 넘으면 updatePasswordRequired -> true

    return loginUser;
  }

  public User getUserInfo(UserDto userDto) {
    Query query = new Query();
    query.addCriteria(Criteria.where("userId").is(userDto.getUserId()));
    User user = mongoTemplate.findOne(query, User.class, "user");
    return user;
  }

  public UpdateResult updateNickName(UserDto toUpdateUserDto) {
    Query query = new Query();
    query.addCriteria(Criteria.where("userId").is(toUpdateUserDto.getUserId()));

    Update update = new Update();
    update.set("nickname", toUpdateUserDto.getNickname());
    // cannotfindexception
    UpdateResult updateResult = mongoTemplate.upsert(query, update, "user");
    return updateResult;
  }

  public UpdateResult updatePassword(UserDto toUpdateUserDto) {
    String encodedNewPassword = passwordEncoder.encode(toUpdateUserDto.getPassword());
    String newUpdatedDatetime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    Query query = new Query();
    query.addCriteria(Criteria.where("userId").is(toUpdateUserDto.getUserId()));

    Update update = new Update();
    update.set("password", encodedNewPassword);
    update.set("updatedDatetime", newUpdatedDatetime);
    UpdateResult updatedResult = mongoTemplate.updateMulti(query, update, "user");

    return updatedResult;
  }

  public String delete(@RequestBody String userId) {
    Query query = new Query();
    query.addCriteria(Criteria.where("userId").is(userId));
    mongoTemplate.remove(query, User.class, "user");

    return "회원 탈퇴가 완료되었습니다.";
  }
}
