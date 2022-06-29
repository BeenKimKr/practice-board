package kb.practiceboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class Application {

  public static void main(String[] args) throws Exception {
    SpringApplication.run(Application.class, args);
  }
}
