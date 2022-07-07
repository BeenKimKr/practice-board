package kb.practiceboard;

import com.fasterxml.jackson.databind.ObjectMapper;
import kb.practiceboard.controller.PostingController;
import kb.practiceboard.dto.posting.PostingCreateDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
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
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
public class PostingControllerTest {

  MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private PostingController postingController;

  @MockBean
  private MappingMongoConverter mappingMongoConverter;

  @BeforeEach
  public void setUp(WebApplicationContext webApplicationContext,
                    RestDocumentationContextProvider restDocumentationContextProvider) {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
        .apply(documentationConfiguration(restDocumentationContextProvider))
        .addFilters(new CharacterEncodingFilter("UTF-8", true))
        .build();
  }

  @DisplayName("게시믈 생성")
  @Test
  void createPosting() throws Exception {
    PostingCreateDto posting = PostingCreateDto.builder()
        .title("테스트 제목입니다.")
        .contents("테스트 내용입니다.")
        .authorId("c0da61d6-f259-412b-b6d6-2c0a890c7744")
        .boardId("62be6f64eb2cb135300b0728")
        .build();

    ResultActions result = mockMvc.perform(
        post("/posting")
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8")
            .content(objectMapper.writeValueAsString(posting))
    );
    result
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(
            document("createPosting",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("title").description("제목").type(JsonFieldType.STRING),
                    fieldWithPath("contents").description("내용").type(JsonFieldType.STRING),
                    fieldWithPath("authorId").description("작성자 ID").type(JsonFieldType.STRING),
                    fieldWithPath("boardId").description("게시판 ID").type(JsonFieldType.STRING)
                )
//                , responseFields(
//                    subsectionWithPath("response").description("응답"),
//                    fieldWithPath("response.[].title").description("ID").type(JsonFieldType.STRING),
//                    fieldWithPath("response.[].contents").description("내용").type(JsonFieldType.STRING),
//                    fieldWithPath("response.[].authorId").description("작성자 ID").type(JsonFieldType.STRING),
//                    fieldWithPath("response.[].updatedDateTime").description("마지막 수정일시").type(JsonFieldType.STRING)
//                )
            )
        );
  }

  @DisplayName("게시믈 조회")
  @Test
  void viewPosting() throws Exception {
    String postingId = "62be722927bc640846e4a62b";

    ResultActions result = mockMvc.perform(
        post("/posting/{postingId}", postingId)
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8")
    );
    result
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(
            document("viewPosting",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                pathParameters(
                    parameterWithName("postingId").description("ID")
                )
//                , responseFields(
//                    subsectionWithPath("response").description("응답"),
//                    fieldWithPath("response.[].title").description("ID").type(JsonFieldType.STRING),
//                    fieldWithPath("response.[].contents").description("내용").type(JsonFieldType.STRING),
//                    fieldWithPath("response.[].authorId").description("작성자 ID").type(JsonFieldType.STRING),
//                    fieldWithPath("response.[].updatedDateTime").description("마지막 수정일시").type(JsonFieldType.STRING),
//                    fieldWithPath("response.[].fileId").description("첨부파일 목록").type(JsonFieldType.ARRAY)
//                )
            )
        );
  }

  @DisplayName("게시물 수정")
  @Test
  void editPosting() throws Exception {
    String postingId = "62be722927bc640846e4a62b";

    PostingCreateDto posting = PostingCreateDto.builder()
        .title("테스트 수정된 제목입니다.")
        .contents("테스트 수정된 내용입니다.")
        .build();

    ResultActions result = mockMvc.perform(
        patch("/posting/{postingId}", postingId)
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8")
            .content(objectMapper.writeValueAsString(posting))
    );
    result
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(
            document("editPosting",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                pathParameters(
                    parameterWithName("postingId").description("ID")
                ),
                requestFields(
                    fieldWithPath("title").description("제목").type(JsonFieldType.STRING),
                    fieldWithPath("contents").description("내용").type(JsonFieldType.STRING)
                )
//                , responseFields(
//                    subsectionWithPath("response").description("응답"),
//                    fieldWithPath("response.[].title").description("ID").type(JsonFieldType.STRING),
//                    fieldWithPath("response.[].contents").description("내용").type(JsonFieldType.STRING),
//                    fieldWithPath("response.[].authorId").description("작성자 ID").type(JsonFieldType.STRING),
//                    fieldWithPath("response.[].updatedDateTime").description("마지막 수정일시").type(JsonFieldType.STRING),
//                    fieldWithPath("response.[].fileId").description("첨부파일 목록").type(JsonFieldType.ARRAY)
//                )
            )
        );
  }

  @DisplayName("게시글 전체 목록")
  @Test
  void listAllPosting() throws Exception {
    ResultActions result = mockMvc.perform(
        get("/postings")
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8")
    );
    result
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(
            document("listAllPosting",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())
//                , responseFields(
//                    subsectionWithPath("response").description("응답"),
//                    fieldWithPath("response.[].title").description("ID").type(JsonFieldType.STRING),
//                    fieldWithPath("response.[].contents").description("내용").type(JsonFieldType.STRING),
//                    fieldWithPath("response.[].authorId").description("작성자 ID").type(JsonFieldType.STRING),
//                    fieldWithPath("response.[].updatedDateTime").description("마지막 수정일시").type(JsonFieldType.STRING)
//                )
            )
        );
  }

  @DisplayName("검색 기준별 목록")
  @Test
  void listByKeyword() throws Exception {
    String criterion = "author";
    String keyword = "noname";

    ResultActions result = mockMvc.perform(
        get("/postings/{criterion}/{keyword}", criterion, keyword)
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8")
    );
    result
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(
            document("listByKeywords",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                pathParameters(
                    parameterWithName("criterion").description("검색 기준"),
                    parameterWithName("keyword").description("검색 단어")
                )
//                , responseFields(
//                    subsectionWithPath("response").description("응답"),
//                    fieldWithPath("response.[].title").description("제목").type(JsonFieldType.STRING),
//                    fieldWithPath("response.[].contents").description("내용").type(JsonFieldType.STRING),
//                    fieldWithPath("response.[].authorId").description("작성자 ID").type(JsonFieldType.STRING),
//                    fieldWithPath("response.[].updatedDateTime").description("마지막 수정일시").type(JsonFieldType.STRING)
//                )
            )
        );
  }

  @DisplayName("게시물 삭제")
  @Test
  void deleteByIdPosting() throws Exception {
    String postingId = "62be722927bc640846e4a62b";

    ResultActions result = mockMvc.perform(
        delete("/boards/{postingId}", postingId)
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8")
    );
    result
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(
            document("deleteByIdPosting",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                pathParameters(
                    parameterWithName("postingId").description("ID")
                )
                , responseFields(
                    subsectionWithPath("response").description("응답"),
                    fieldWithPath("response.[].message").description("메시지").type(JsonFieldType.STRING)
                )
            )
        );
  }

  @DisplayName("게시판판별 게시글 목록")
  @Test
  void listByBoardId() throws Exception {
    String boardId = "62be6f64eb2cb135300b0728";

    ResultActions result = mockMvc.perform(
        get("board/{boardId}/postings", boardId)
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8")
    );
    result
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(
            document("listByBoardId",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                pathParameters(
                    parameterWithName("boardId").description("게시판 ID")
                )
//                , responseFields(
//                    subsectionWithPath("response").description("응답"),
//                    fieldWithPath("response.[].title").description("게시판 이름").type(JsonFieldType.STRING),
//                    fieldWithPath("response.[].contents").description("게시판 태그").type(JsonFieldType.ARRAY),
//                    fieldWithPath("response.[].authorId").description("작성자 ID").type(JsonFieldType.STRING),
//                    fieldWithPath("response.[].updatedDateTime").description("마지막 수정일시").type(JsonFieldType.STRING)
//                )
            )
        );
  }
}