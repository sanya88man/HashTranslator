package tt.hashtranslator.service;

import tt.hashtranslator.dto.ApplicationDto;
import tt.hashtranslator.entity.Application;

public interface ApplicationService {
    Long decodeHashes(ApplicationDto applicationDto);

    Application getById(long id);
}