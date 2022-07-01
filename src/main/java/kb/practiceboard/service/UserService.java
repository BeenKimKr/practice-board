package kb.practiceboard.service;

import kb.practiceboard.domain.User;
import kb.practiceboard.domain.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
public class UserService {

  private MongoTemplate mongoTemplate;
  private PostingService postingService;

  @Autowired
  public UserService(MongoTemplate mongoTemplate, PostingService postingService) {
    this.mongoTemplate = mongoTemplate;
    this.postingService = postingService;
  }

  @Autowired
  public void setMongoTemplate(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  public User findUserByUserId(String userId) {
    Query query = new Query();
    query.addCriteria(Criteria.where("userId").is(userId));
    User user = mongoTemplate.findOne(query, User.class, "user");
    return user;
  }

  public User findByEmail(String email) {
    Query query = new Query();
    query.addCriteria(Criteria.where("email").is(email));
    User user = mongoTemplate.findOne(query, User.class, "user");
    return user;
  }

  @Transactional
  public User create(@Valid UserDto userDto) {
    String email = userDto.getEmail();
    String uuid = UUID.randomUUID().toString();
    String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    User newUser = User.builder()
        .userId(uuid)
        .email(userDto.getEmail())
        .userName(userDto.getUserName())
        .password(userDto.getPassword())
        .registerDateTime(dateTime)
        .updatedDateTime(dateTime)
        .updatePasswordRequired(false)
        .build();

    return mongoTemplate.insert(newUser, "user");
  }

  @Transactional
  public User login(UserDto userDto) {
    User user = findByEmail(userDto.getEmail());

    // 로그인시 현재 DateTime - updatedDatetime = 90일이 넘으면 updatePasswordRequired -> true
    LocalDate currentDate = LocalDate.now();
    LocalDate updatedDate = LocalDate.parse(user.getUpdatedDateTime().split(" ")[0]);
    long days = ChronoUnit.DAYS.between(updatedDate, currentDate);
    if (days > 90) {
      Query query = new Query();
      query.addCriteria(Criteria.where("userId").is(user.getUserId()));

      Update update = new Update();
      update.set("updatePasswordRequired", true);
      mongoTemplate.updateFirst(query, update, "user");
      return findUserByUserId(user.getUserId());
    }
    return user;
  }

  @Transactional
  public String updateNickName(UserDto toUpdateUserDto) {
    Query query = new Query();
    query.addCriteria(Criteria.where("userId").is(toUpdateUserDto.getUserId()));

    Update update = new Update();
    update.set("nickname", toUpdateUserDto.getNickname());
    mongoTemplate.updateFirst(query, update, "user");
    return "닉네임이 변경되었습니다.";
  }

  @Transactional
  public String updatePassword(UserDto toUpdateUserDto) {
    Query query = new Query();
    query.addCriteria(Criteria.where("userId").is(toUpdateUserDto.getUserId()));

    String newUpdatedDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    Update update = new Update();
    update.set("password", toUpdateUserDto.getPassword());
    update.set("updatedDateTime", newUpdatedDateTime);
    mongoTemplate.updateMulti(query, update, "user");
    return "비밀번호가 변경되었습니다.";
  }

  @Transactional
  public String delete(String userId) {
    Query query = new Query();
    query.addCriteria(Criteria.where("userId").is(userId));
    postingService.deleteAll(userId);
    mongoTemplate.remove(query, User.class, "user");
    return "회원 탈퇴가 완료되었습니다.";
  }
}
