package kb.practiceboard;

import com.fasterxml.jackson.databind.ObjectMapper;
import kb.practiceboard.controller.UserController;
import kb.practiceboard.dto.user.UserIdDto;
import kb.practiceboard.dto.user.UserLoginDto;
import kb.practiceboard.dto.user.UserPatchPasswordDto;
import kb.practiceboard.dto.user.UserRegisterDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
public class UserControllerTest {

  MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @InjectMocks
  private UserController userController;

  @BeforeEach
  public void setUp(WebApplicationContext webApplicationContext,
                    RestDocumentationContextProvider restDocumentationContextProvider) {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
        .apply(documentationConfiguration(restDocumentationContextProvider))
        .addFilters(new CharacterEncodingFilter("UTF-8", true))
        .build();
  }

  @DisplayName("회원가입")
  @Test
  void registerUser() throws Exception {
    UserRegisterDto user = UserRegisterDto.builder()
        .email("aaa@test1.com")
        .userName("aaaaaaaaa")
        .password("aaa12345!!")
        .build();

    ResultActions result = mockMvc.perform(
        post("/user")
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8")
            .accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(user))
    );
    result
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(
            document("registerUser",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("email").description("이메일").type(JsonFieldType.STRING),
                    fieldWithPath("userName").description("이름").type(JsonFieldType.STRING),
                    fieldWithPath("password").description("비밀번호").type(JsonFieldType.STRING)
                ),
                responseFields(
                    fieldWithPath("email").description("이메일").type(JsonFieldType.STRING),
                    fieldWithPath("userName").description("이름").type(JsonFieldType.STRING).optional(),
                    fieldWithPath("password").description("비밀번호").type(JsonFieldType.STRING).optional(),
                    fieldWithPath("updatedPasswordRequired").description("비밀번호 변경 필요 여부").type(JsonFieldType.BOOLEAN).optional()
                )
            )
        );
  }

  @DisplayName("로그인")
  @Test
  void loginUser() throws Exception {
    UserLoginDto user = UserLoginDto.builder()
        .email("aaa@test1.com")
        .password("aaa12345!!")
        .build();

    ResultActions result = mockMvc.perform(
        patch("/user/login")
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8")
            .accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(user))
    );
    result
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(
            document("loginUser",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("userId").description("ID").type(JsonFieldType.STRING).optional(),
                    fieldWithPath("nickname").description("닉네임").type(JsonFieldType.STRING).optional(),
                    fieldWithPath("updatePasswordRequired").description("비밀번호 변경 필요 여부").type(JsonFieldType.BOOLEAN).optional(),
                    fieldWithPath("email").description("이메일").type(JsonFieldType.STRING),
                    fieldWithPath("password").description("비밀번호").type(JsonFieldType.STRING)
                ),
                responseFields(
                    fieldWithPath("userId").description("ID").type(JsonFieldType.STRING),
                    fieldWithPath("nickname").description("닉네임").type(JsonFieldType.STRING),
                    fieldWithPath("updatePasswordRequired").description("비밀번호 변경 필요 여부").type(JsonFieldType.BOOLEAN),
                    fieldWithPath("email").description("이메일").type(JsonFieldType.STRING).optional()
                )
            )
        );
  }

  /*
  @DisplayName("닉네임 변경")
  @Test
  void updateNickname() throws Exception {
    UserPatchNicknameDto user = UserPatchNicknameDto.builder()
        .userId("fc187b2f-0c34-4eea-8587-bd3b1f23dd05")
        .nickname("aaa")
        .build();

    ResultActions result = mockMvc.perform(
        patch("/user/nickname")
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8")
            .accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(user))
    );
    result
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(
            document("updateNickname",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("userId").description("ID").type(JsonFieldType.STRING),
                    fieldWithPath("nickname").description("닉네임").type(JsonFieldType.STRING)
                ),
                responseFields(
                    fieldWithPath("nickname").description("닉네임").type(JsonFieldType.STRING),
                    fieldWithPath("userId").description("ID").type(JsonFieldType.STRING).optional(),
                    fieldWithPath("updatePasswordRequired").description("비밀번호 변경 필요 여부").type(JsonFieldType.BOOLEAN).optional(),
                    fieldWithPath("email").description("이메일").type(JsonFieldType.STRING).optional()
                )
            )
        );
  }
*/

  @DisplayName("비밀번호 변경")
  @Test
  public void updatePasswordUser() throws Exception {
    UserPatchPasswordDto user = UserPatchPasswordDto.builder()
        .userId("fc187b2f-0c34-4eea-8587-bd3b1f23dd05")
        .password("aaa123123!!")
        .build();

    ResultActions result = mockMvc.perform(
        patch("/user/pwd")
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8")
            .accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(user))
    );
    result
        .andExpect(status().isOk())
        .andDo(
            print())
        .andDo(document("updatePasswordUser",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("userId").description("ID").type(JsonFieldType.STRING),
                    fieldWithPath("password").description("비밀번호").type(JsonFieldType.STRING)
                ),
                responseFields(
                    fieldWithPath("message").description("메시지").type(JsonFieldType.STRING)
                )
            )
        );
  }

  /*
  @DisplayName("유저 정보 조회")
  @Test
  void myAccount() throws Exception {
    UserIdDto user = UserIdDto.builder()
        .userId("fc187b2f-0c34-4eea-8587-bd3b1f23dd05")
        .build();

    ResultActions result = this.mockMvc.perform(
        get("/user")
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8")
            .accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(user))
    );
    result
//        .andExpect(status().isOk())
        .andDo(print())
        .andDo(
            document("myAccount",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("userId").description("ID").type(JsonFieldType.STRING)
                ),
                responseFields(
                    fieldWithPath("nickname").description("닉네임").type(JsonFieldType.STRING),
                    fieldWithPath("updatePasswordRequired").description("비밀번호 변경 필요 여부").type(JsonFieldType.BOOLEAN),
                    fieldWithPath("email").description("이메일").type(JsonFieldType.STRING).optional(),
                    fieldWithPath("userId").description("ID").type(JsonFieldType.STRING).optional()
                )
            )
        );
  }
   */

  @DisplayName("회원탈퇴")
  @Test
  void deleteUser() throws Exception {
    UserIdDto user = UserIdDto.builder()
        .userId("fc187b2f-0c34-4eea-8587-bd3b1f23dd05")
        .build();

    ResultActions result = mockMvc.perform(
        delete("/user")
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8")
            .accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(user))
    );
    result
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(
            document("deleteUser",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("userId").description("ID").type(JsonFieldType.STRING)
                ),
                responseFields(
                    fieldWithPath("message").description("메시지").type(JsonFieldType.STRING)
                )
            )
        );
  }
}
