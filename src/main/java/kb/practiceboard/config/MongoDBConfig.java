package kb.practiceboard.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoDBConfig {
  private final MongoDatabaseFactory mongoDatabaseFactory;

  public MongoDBConfig(MongoDatabaseFactory mongoDatabaseFactory) {
    this.mongoDatabaseFactory = mongoDatabaseFactory;
  }

  @Bean
  public MongoTemplate mongoTemplate() {
    return new MongoTemplate(mongoDatabaseFactory);
  }
}
