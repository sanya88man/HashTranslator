package tt.hashtranslator.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import tt.hashtranslator.dto.ApplicationDto;
import tt.hashtranslator.entity.Application;
import tt.hashtranslator.exception.CommonException;
import tt.hashtranslator.repository.ApplicationRepository;
import tt.hashtranslator.service.ApplicationService;
import tt.hashtranslator.service.HashService;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final HashService hashService;

    @Override
    public Long decodeHashes(ApplicationDto applicationDto) {
        Application application = new Application();
        application.setHashes(applicationDto.getHashes());
        Application savedApplication = applicationRepository.save(application);
        hashService.decode(savedApplication);
        return savedApplication.getId();
    }

    @Override
    public Application getById(long id) {
        return applicationRepository.findById(id)
                .orElseThrow(() -> new CommonException(
                        String.format("Application with id %d not found", id), HttpStatus.NOT_FOUND.value()));
    }
}
