package kb.practiceboard.service;

import kb.practiceboard.domain.UserEntity;
import kb.practiceboard.dto.user.UserLoginDto;
import kb.practiceboard.dto.user.UserNicknameDto;
import kb.practiceboard.dto.user.UserPasswordDto;
import kb.practiceboard.dto.user.UserRegisterDto;
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

  public UserEntity findUserByUserId(String userId) {
    Query query = new Query();
    query.addCriteria(Criteria.where("userId").is(userId));
    UserEntity user = mongoTemplate.findOne(query, UserEntity.class, "user");
    return user;
  }

  public UserEntity findByEmail(String email) {
    Query query = new Query();
    query.addCriteria(Criteria.where("email").is(email));
    UserEntity user = mongoTemplate.findOne(query, UserEntity.class, "user");
    return user;
  }

  @Transactional
  public UserEntity create(@Valid UserRegisterDto userRegisterDto) {
    String uuid = UUID.randomUUID().toString();
    String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    UserEntity newUser = UserEntity.builder()
        .userId(uuid)
        .email(userRegisterDto.getEmail())
        .userName(userRegisterDto.getUserName())
        .password(userRegisterDto.getPassword())
        .registerDateTime(dateTime)
        .passwordUpdatedDateTime(dateTime)
        .updatePasswordRequired(false)
        .build();

    return mongoTemplate.insert(newUser, "user");
  }

  @Transactional
  public UserEntity login(UserLoginDto userLoginDto) {
    UserEntity user = findByEmail(userLoginDto.getEmail());

    // 로그인시 현재 DateTime - updatedDatetime = 90일이 넘으면 updatePasswordRequired -> true
    LocalDate currentDate = LocalDate.now();
    LocalDate updatedDate = LocalDate.parse(user.getPasswordUpdatedDateTime().split(" ")[0]);
    long days = ChronoUnit.DAYS.between(updatedDate, currentDate);
    Query query = new Query();
    query.addCriteria(Criteria.where("userId").is(user.getUserId()));

    Update update = new Update();
    if (days > 90) {
      update.set("updatePasswordRequired", true);
      mongoTemplate.updateFirst(query, update, "user");
      return findUserByUserId(user.getUserId());
    } else {
      user.builder()
          .updatePasswordRequired(false)
          .build();
      update.set("updatePasswordRequired", false);
      mongoTemplate.updateFirst(query, update, "user");
      return user;
    }
  }

  @Transactional
  public String updateNickName(String userId, UserNicknameDto userNicknameDto) {
    Query query = new Query();
    query.addCriteria(Criteria.where("userId").is(userId));

    Update update = new Update();
    update.set("nickname", userNicknameDto.getNickname());
    mongoTemplate.updateFirst(query, update, "user");
    return "닉네임이 변경되었습니다.";
  }

  @Transactional
  public String updatePassword(String userId, UserPasswordDto userPasswordDto) {
    Query query = new Query();
    query.addCriteria(Criteria.where("userId").is(userId));

    String newUpdatedDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    Update update = new Update();
    update.set("password", userPasswordDto.getPassword());
    update.set("updatedDateTime", newUpdatedDateTime);
    mongoTemplate.updateMulti(query, update, "user");
    return "비밀번호가 변경되었습니다.";
  }

  @Transactional
  public String delete(String userId) {
    Query query = new Query();
    query.addCriteria(Criteria.where("userId").is(userId));
    postingService.deleteAll(userId);
    mongoTemplate.remove(query, UserEntity.class, "user");
    return "회원 탈퇴가 완료되었습니다.";
  }
}
