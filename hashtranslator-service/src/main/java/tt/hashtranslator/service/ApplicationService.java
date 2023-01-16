package tt.hashtranslator.service;

import tt.hashtranslator.dto.ApplicationDto;
import tt.hashtranslator.entity.Application;

public interface ApplicationService {
    String process(ApplicationDto applicationDto);

    Application getById(String id);
}
