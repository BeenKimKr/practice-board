package kb.practiceboard;

import kb.practiceboard.controller.UserController;
import kb.practiceboard.dto.user.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserRestDocsTest {

  @Autowired
  private WebApplicationContext context;
  private MockMvc mockMvc;

  @MockBean
  private UserController userController;

  @BeforeEach
  public void setUp(RestDocumentationContextProvider restDocumentation) {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
        .apply(documentationConfiguration(restDocumentation))
        .build();
  }

  @DisplayName("회원가입")
  @Test
  public void registerUser() throws Exception {
    UserRegisterDto user = UserRegisterDto.builder()
        .email("aaa@test1.com")
        .userName("noname")
        .password("aaa12345!!")
        .build();

    UserRegisterDto resultUser = UserRegisterDto.builder()
        .email("aaa@test1.com")
        .build();
    given(userController.registerUser(user)).willReturn(resultUser);

    ResultActions result = mockMvc.perform(
        post("/user")
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8")
            .accept(MediaType.APPLICATION_JSON)
    );
    result.andExpect(status().isOk())
        .andDo(print())
        .andDo(document("{UserRestDocsTest}/{registerUser}",
            preprocessRequest(prettyPrint()),
            preprocessResponse(prettyPrint()),
            requestFields(
                fieldWithPath("email").description("이메일").type(JsonFieldType.STRING),
                fieldWithPath("userName").description("이름").type(JsonFieldType.STRING),
                fieldWithPath("password").description("비밀번호").type(JsonFieldType.STRING)
            ),
            responseFields(
                subsectionWithPath("response").description("응답"),
                fieldWithPath("response.[].email").description("이메일").type(JsonFieldType.STRING)
            ))
        );
  }

  @DisplayName("로그인")
  @Test
  public void loginUser() throws Exception {
    UserLoginRequestDto user = UserLoginRequestDto.builder()
        .email("aaa@test1.com")
        .password("aaa12345!!")
        .build();

    UserLoginResponseDto resultUser = UserLoginResponseDto.builder()
        .userId("3f2454a4-360f-45ea-8681-526e943236b8")
        .nickname("noname")
        .updatePasswordRequired(true)
        .build();
    given(userController.loginUser(user)).willReturn(resultUser);

    ResultActions result = mockMvc.perform(
        patch("/user/login")
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8")
            .accept(MediaType.APPLICATION_JSON)
    );
    result.andExpect(status().isOk())
        .andDo(print())
        .andDo(document("{UserRestDocsTest}/{loginUser}",
            preprocessRequest(prettyPrint()),
            preprocessResponse(prettyPrint()),
            requestFields(
                fieldWithPath("email").description("이메일").type(JsonFieldType.STRING),
                fieldWithPath("password").description("비밀번호").type(JsonFieldType.STRING)
            ),
            responseFields(
                subsectionWithPath("response").description("응답"),
                fieldWithPath("response.[].userId").description("ID").type(JsonFieldType.STRING),
                fieldWithPath("response.[].nickname").description("닉네임").type(JsonFieldType.STRING),
                fieldWithPath("response.[].updatePasswordRequired").description("비밀번호 변경 필요 여부").type(JsonFieldType.BOOLEAN)
            ))
        );
  }

  @DisplayName("닉네임 변경")
  @Test
  public void updateNickname() throws Exception {
    UserPatchNicknameDto user = UserPatchNicknameDto.builder()
        .userId("fc187b2f-0c34-4eea-8587-bd3b1f23dd05")
        .nickname("aaa")
        .build();

    UserPatchNicknameDto resultUser = UserPatchNicknameDto.builder()
        .nickname("aaa")
        .build();
    given(userController.updateNickname(user)).willReturn(resultUser);

    ResultActions result = mockMvc.perform(
        patch("/user/nickname")
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8")
            .accept(MediaType.APPLICATION_JSON)
    );
    result.andExpect(status().isOk())
        .andDo(print())
        .andDo(document("{UserRestDocsTest}/{updateNickname}",
            preprocessRequest(prettyPrint()),
            preprocessResponse(prettyPrint()),
            requestFields(
                fieldWithPath("userId").description("ID").type(JsonFieldType.STRING),
                fieldWithPath("nickname").description("닉네임").type(JsonFieldType.STRING)
            ),
            responseFields(
                subsectionWithPath("response").description("응답"),
                fieldWithPath("response.[].nickname").description("닉네임").type(JsonFieldType.STRING)
            ))
        );
  }

  @DisplayName("비밀번호 변경")
  @Test
  public void updatePasswordUser() throws Exception {
    UserPatchPasswordDto user = UserPatchPasswordDto.builder()
        .userId("fc187b2f-0c34-4eea-8587-bd3b1f23dd05")
        .password("aaa123123!!")
        .build();

    String message = "비밀번호가 변경되었습니다.";
    given(userController.updatePasswordUser(user)).willReturn(message);

    ResultActions result = mockMvc.perform(
        patch("/user/password")
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8")
            .accept(MediaType.APPLICATION_JSON)
    );
    result.andExpect(status().isOk())
        .andDo(print())
        .andDo(document("{UserRestDocsTest}/{updatePasswordUser}",
            preprocessRequest(prettyPrint()),
            preprocessResponse(prettyPrint()),
            requestFields(
                fieldWithPath("userId").description("ID").type(JsonFieldType.STRING),
                fieldWithPath("password").description("비밀번호").type(JsonFieldType.STRING)
            ),
            responseFields(
                subsectionWithPath("response").description("응답"),
                fieldWithPath("response.[].message").description("메시지").type(JsonFieldType.STRING)
            ))
        );
  }

  @DisplayName("유저 정보 조회")
  @Test
  public void myaccount() throws Exception {
    String userId = "fc187b2f-0c34-4eea-8587-bd3b1f23dd05";
    String nickname = "abc";
    given(userController.myaccount(userId)).willReturn(nickname);

    ResultActions result = mockMvc.perform(
        get("/user/myaccount")
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8")
            .accept(MediaType.APPLICATION_JSON)
    );
    result.andExpect(status().isOk())
        .andDo(print())
        .andDo(document("{UserRestDocsTest}/{myaccount}",
            preprocessRequest(prettyPrint()),
            preprocessResponse(prettyPrint()),
            requestFields(
                fieldWithPath("userId").description("ID").type(JsonFieldType.STRING)
            ),
            responseFields(
                subsectionWithPath("response").description("응답"),
                fieldWithPath("response.[].nickname").description("닉네임").type(JsonFieldType.STRING)
            ))
        );
  }

  @DisplayName("회원탈퇴")
  @Test
  public void deleteUser() throws Exception {
    String userId = "fc187b2f-0c34-4eea-8587-bd3b1f23dd05";
    String message = "회원 탈퇴가 완료되었습니다.";
    given(userController.deleteUser(userId)).willReturn(message);

    ResultActions result = mockMvc.perform(
        delete("/user")
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8")
            .accept(MediaType.APPLICATION_JSON)
    );
    result.andExpect(status().isOk())
        .andDo(print())
        .andDo(document("{UserRestDocsTest}/{deleteUser}",
            preprocessRequest(prettyPrint()),
            preprocessResponse(prettyPrint()),
            requestFields(
                fieldWithPath("userId").description("ID").type(JsonFieldType.STRING)
            ),
            responseFields(
                subsectionWithPath("response").description("응답"),
                fieldWithPath("response.[].message").description("메시지").type(JsonFieldType.STRING)
            ))
        );
  }
}
