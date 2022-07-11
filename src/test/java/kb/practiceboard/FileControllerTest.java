package kb.practiceboard;

import com.fasterxml.jackson.databind.ObjectMapper;
import kb.practiceboard.controller.FileController;
import kb.practiceboard.dto.file.FileDto;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
public class FileControllerTest {

  MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @InjectMocks
  private FileController fileController;

  @BeforeEach
  public void setUp(WebApplicationContext webApplicationContext,
                    RestDocumentationContextProvider restDocumentationContextProvider) {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
        .apply(documentationConfiguration(restDocumentationContextProvider))
        .addFilters(new CharacterEncodingFilter("UTF-8", true))
        .build();
  }

  @DisplayName("파일 목록")
  @Test
  void fileListByPostingId() throws Exception {
    FileDto file = FileDto.builder()
        .postingId("62bea9f0fb954b34b82cfd36")
        .build();

    ResultActions result = mockMvc.perform(
        get("/files")
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8")
            .accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(file))
    );
    result
//        .andExpect(status().isOk())
        .andDo(print())
        .andDo(
            document("fileListByPostingId",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("postingId").description("게시글 ID").type(JsonFieldType.STRING)
                ),
                responseFields(
                    fieldWithPath("[].originalName").description("파일명").type(JsonFieldType.STRING),
                    fieldWithPath("[].size").description("파일 크기").type(JsonFieldType.STRING).optional(),
                    fieldWithPath("[].mimeType").description("MIME").type(JsonFieldType.STRING),
                    fieldWithPath("[].uploaderId").description("업로더 ID").type(JsonFieldType.STRING)
                )
            )
        );
  }

  @DisplayName("파일 삭제")
  @Test
  void deleteFile() throws Exception {
    String fileId = "442fb8e9-5850-4d16-81be-e2d1e260ff78";

    ResultActions result = mockMvc.perform(
        delete("/file")
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("UTF-8")
            .accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(fileId))
    );
    result
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(
            document("deleteFile",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("message").description("메시지").type(JsonFieldType.STRING)
                )
            )
        );
  }
}
