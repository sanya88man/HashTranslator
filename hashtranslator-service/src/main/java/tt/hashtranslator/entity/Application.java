package tt.hashtranslator.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Application entity.
 */
@Data
@Document
public class Application {
    @Id
    private String id;
    private List<String> hashes;
}
