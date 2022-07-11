package kb.practiceboard;


import com.fasterxml.jackson.databind.ObjectMapper;
import kb.practiceboard.controller.BoardController;
import kb.practiceboard.dto.board.BoardCreateDto;
import kb.practiceboard.dto.board.BoardDto;
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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
public class BoardControllerTest {

  MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @InjectMocks
  private BoardController boardController;

  @BeforeEach
  public void setUp(WebApplicationContext webApplicationContext,
                    RestDocumentationContextProvider restDocumentationContextProvider) {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
        .apply(documentationConfiguration(restDocumentationContextProvider))
        .addFilters(new CharacterEncodingFilter("UTF-8", true))
        .build();
  }

  @DisplayName("게시판 생성")
  @Test
  void createBoard() throws Exception {
    List<String> tag = new ArrayList<>();
    tag.add("자유");

    String currentDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    BoardCreateDto board = BoardCreateDto.builder()
        .boardName("자유게시판")
        .tag(tag)
        .build();

    ResultActions result = mockMvc.perform(
        post("/board")
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8")
            .content(objectMapper.writeValueAsString(board))
    );
    result
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(
            document("createBoard",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("boardName").description("게시판 이름").type(JsonFieldType.STRING),
                    fieldWithPath("tag").description("게시판 태그").type(JsonFieldType.ARRAY)
                ),
                responseFields(
                    fieldWithPath("boardName").description("게시판명").type(JsonFieldType.STRING),
                    fieldWithPath("tag").description("태그").type(JsonFieldType.ARRAY),
                    fieldWithPath("lastPostingDateTime").description("마지막 게시물 생성 시간").type(JsonFieldType.STRING)
                )
            )
        );
  }

  @DisplayName("게시판 전체 목록")
  @Test
  void allBoardList() throws Exception {
    ResultActions result = mockMvc.perform(
        get("/boards")
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8")
    );
    result
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(
            document("allBoardList",
                preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("[].boardName").description("게시판명").type(JsonFieldType.STRING),
                    fieldWithPath("[].tag").description("게시판 태그").type(JsonFieldType.ARRAY),
                    fieldWithPath("[].lastPostingDateTime").description("마지막 게시물 생성 시간").type(JsonFieldType.STRING)
                )
            )
        );
  }

  @DisplayName("게시판 태그 수정")
  @Test
  void updateTag() throws Exception {
    List<String> tag = new ArrayList<>();
    tag.add("t");
    tag.add("e");
    tag.add("s");

    BoardDto board = BoardDto.builder()
        .boardName("자유게시판")
        .tag(tag)
        .build();

    ResultActions result = mockMvc.perform(
        patch("/board")
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8")
            .accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(board))
    );
    result
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(
            document("updateTag",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("boardName").description("게시판 이름").type(JsonFieldType.STRING),
                    fieldWithPath("tag").description("게시판 태그").type(JsonFieldType.ARRAY),
                    fieldWithPath("lastPostingDateTime").description("마지막 게시물 생성 시간").type(JsonFieldType.STRING).optional()
                ),
                responseFields(
                    fieldWithPath("tag").description("게시판 태그").type(JsonFieldType.ARRAY)
                )
            )
        );
  }

  @DisplayName("검색 기준별 목록")
  @Test
  void listByKeywords() throws Exception {
    String criterion = "boardName";
    String keyword = "자유게시판";

    ResultActions result = mockMvc.perform(
        get("/boards/" + criterion + "/" + keyword)
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
                responseFields(
                    fieldWithPath("[].boardName").description("게시판 이름").type(JsonFieldType.STRING),
                    fieldWithPath("[].tag").description("게시판 태그").type(JsonFieldType.ARRAY),
                    fieldWithPath("[].lastPostingDateTime").description("마지막 게시물 생성 시간").type(JsonFieldType.STRING)
                )
            )
        );
  }
}
