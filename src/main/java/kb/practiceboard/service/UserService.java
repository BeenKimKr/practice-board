package kb.practiceboard.service;

import com.mongodb.client.result.UpdateResult;
import kb.practiceboard.domain.User;
import kb.practiceboard.domain.UserDto;
import kb.practiceboard.handler.AlreadyExistEmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
  // 로그인시 updatedDatetime 3개월인지 체크 추가
  public User login(UserDto userDto) {
    User loginUser = findByEmail(userDto.getEmail());
    // email이 존재하지 않을 때 error
    Boolean checkAuthentication = true;

//    if (!passwordEncoder.matches(user.getPassword(), user.getPassword())) {
//      throw new Exception(PasswordWrongException);
//       PasswordWrongException에서 비밀번호 error처리
//    }
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

  // password validation 중복 체크
  public UpdateResult updatePassword(UserDto toUpdateUserDto) {
    String encodedNewPassword = passwordEncoder.encode(toUpdateUserDto.getPassword());
    String newUpdatedDatetime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    Query query = new Query();
    query.addCriteria(Criteria.where("userId").is(toUpdateUserDto.getUserId()));

    Update update = new Update();
    update.set("password", encodedNewPassword);
    update.set("updatedDatetime", newUpdatedDatetime);
    // cannotfindexception
    UpdateResult updatedResult = mongoTemplate.updateMulti(query, update, "user");

    return updatedResult;
  }

  public String delete(String userId) {
    Query query = new Query();
    query.addCriteria(Criteria.where("userId").is(userId));
    mongoTemplate.remove(query, User.class, "user");

    return "회원 탈퇴가 완료되었습니다.";
  }
}
