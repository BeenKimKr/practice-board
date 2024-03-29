package kb.practiceboard;

import com.fasterxml.jackson.databind.ObjectMapper;
import kb.practiceboard.controller.CommentController;
import kb.practiceboard.dto.comment.CommentDto;
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
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
public class CommentControllerTest {

  MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @InjectMocks
  private CommentController commentController;

  @BeforeEach
  public void setUp(WebApplicationContext webApplicationContext,
                    RestDocumentationContextProvider restDocumentationContextProvider) {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
        .apply(documentationConfiguration(restDocumentationContextProvider))
        .addFilters(new CharacterEncodingFilter("UTF-8", true))
        .build();
  }

  @DisplayName("댓글 생성")
  @Test
  void createComment() throws Exception {
    CommentDto comment = CommentDto.builder()
        .writerId("c0da61d6-f259-412b-b6d6-2c0a890c7744")
        .contents("댓글 테스트입니다.")
        .postingId("62bea9f0fb954b34b82cfd36")
        .build();

    ResultActions result = mockMvc.perform(
        post("/comment")
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8")
            .accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(comment))
    );
    result
        .andExpect(status().isCreated())
        .andDo(print())
        .andDo(
            document("createComment",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("writerId").description("작성자 ID").type(JsonFieldType.STRING),
                    fieldWithPath("contents").description("내용").type(JsonFieldType.STRING),
                    fieldWithPath("postingId").description("게시글 ID").type(JsonFieldType.STRING)
                ), responseFields(
                    fieldWithPath("writerId").description("작성자 ID").type(JsonFieldType.STRING),
                    fieldWithPath("contents").description("내용").type(JsonFieldType.STRING),
                    fieldWithPath("postingId").description("게시글 ID").type(JsonFieldType.STRING),
                    fieldWithPath("updatedDateTime").description("마지막 댓글 수정 시간").type(JsonFieldType.STRING)
                )
            )
        );
  }

  @DisplayName("댓글 수정")
  @Test
  void modifyComment() throws Exception {
    CommentDto comment = CommentDto.builder()
        .contents("댓글 수정 테스트입니다.")
        .build();

    String commentId = "62c3ef21dd53f328381bea21";

    ResultActions result = mockMvc.perform(
        patch("/comment/" + commentId)
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8")
            .accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(comment))
    );
    result
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(
            document("modifyComment",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("contents").description("내용").type(JsonFieldType.STRING)
                )
                , responseFields(
                    fieldWithPath(".contents").description("내용").type(JsonFieldType.STRING),
                    fieldWithPath("updatedDateTime").description("마지막 댓글 수정 시간").type(JsonFieldType.STRING)
                )
            )
        );
  }

  @DisplayName("댓글 삭제")
  @Test
  void deleteComment() throws Exception {
    String commentId = "62c3eec9bfcc5225c248ddaf";

    ResultActions result = mockMvc.perform(
        delete("/comment/" + commentId)
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8")
            .accept(MediaType.APPLICATION_JSON)
    );
    result
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(
            document("deleteComment",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("message").description("메시지").type(JsonFieldType.STRING)
                )
            )
        );
  }

  @DisplayName("게시글별 댓글 목록")
  @Test
  void viewComment() throws Exception {
    String postingId = "62bea9f0fb954b34b82cfd36";

    ResultActions result = mockMvc.perform(
        get("/comments/" + postingId)
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8")
            .accept(MediaType.APPLICATION_JSON)
    );
    result
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(
            document("viewComment",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("[].contents").description("내용").type(JsonFieldType.STRING),
                    fieldWithPath("[].postingId").description("게시글 ID").type(JsonFieldType.STRING),
                    fieldWithPath("[].updatedDateTime").description("마지막 댓글 수정 시간").type(JsonFieldType.STRING)
                )
            )
        );
  }

  @DisplayName("내가 쓴 댓글 목록")
  @Test
  void commentListByUserId() throws Exception {
    String userId = "c0da61d6-f259-412b-b6d6-2c0a890c7744";

    ResultActions result = mockMvc.perform(
        get("/comments/my/" + userId)
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8")
            .accept(MediaType.APPLICATION_JSON)
    );
    result
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(
            document("commentListByUserId",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("[].contents").description("내용").type(JsonFieldType.STRING),
                    fieldWithPath("[].postingId").description("게시글 ID").type(JsonFieldType.STRING),
                    fieldWithPath("[].updatedDateTime").description("마지막 댓글 수정 시간").type(JsonFieldType.STRING)
                )
            )
        );
  }
}