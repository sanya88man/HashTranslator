package tt.hashtranslator.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import tt.hashtranslator.enums.Status;

import java.util.List;

@Data
@Document
public class Application {
    @Id
    private Long id;
    private List<String> hashes;
}
