package kb.practiceboard.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;

@Getter
@NoArgsConstructor
public class FileDto {

  private String originalName;
  @Max(value = 8388608, message = "파일은 최대 8MB까지 업로드 가능합니다. 파일 크기를 확인해주세요.")
  private long size;
  private String mimeType;
  private String uploaderId;
  private String postingId;

  @Builder
  public FileDto(String originalName, long size, String mimeType, String uploaderId, String postingId) {
    this.originalName = originalName;
    this.size = size;
    this.mimeType = mimeType;
    this.uploaderId = uploaderId;
    this.postingId = postingId;
  }
}
