package kb.practiceboard.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "file")
@Getter
@NoArgsConstructor
public class FileDto {

  @Id
  private String id;
  private String originalName;
  private long size;
  private String mimeType;
  private String uploaderId;
  private String postingId;

  @Builder
  public FileDto(String id, String originalName, long size, String mimeType, String uploaderId, String postingId) {
    this.id = id;
    this.originalName = originalName;
    this.size = size;
    this.mimeType = mimeType;
    this.uploaderId = uploaderId;
    this.postingId = postingId;
  }
}
