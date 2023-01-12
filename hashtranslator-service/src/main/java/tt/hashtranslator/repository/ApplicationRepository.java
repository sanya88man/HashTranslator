package tt.hashtranslator.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import tt.hashtranslator.entity.Application;

public interface ApplicationRepository extends MongoRepository<Application, Long> {
}
