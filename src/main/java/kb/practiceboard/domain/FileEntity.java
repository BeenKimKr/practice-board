package kb.practiceboard.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "file")
@Getter
@NoArgsConstructor
public class FileEntity {

  @Id
  private ObjectId _id;
  private String originalName;
  private long size;
  private String mimeType;
  private String uploaderId;
  private String postingId;
  private String uploadDateTime;

  @Builder
  public FileEntity(ObjectId _id, String originalName, long size, String mimeType, String uploaderId, String postingId, String uploadDateTime) {
    this._id = _id;
    this.originalName = originalName;
    this.size = size;
    this.mimeType = mimeType;
    this.uploaderId = uploaderId;
    this.postingId = postingId;
    this.uploadDateTime = uploadDateTime;
  }
}
