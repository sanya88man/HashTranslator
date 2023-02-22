package tt.hashtranslator.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tt.hashtranslator.dto.ApplicationDto;
import tt.hashtranslator.entity.Application;
import tt.hashtranslator.exception.CommonException;
import tt.hashtranslator.repository.ApplicationRepository;
import tt.hashtranslator.service.ApplicationService;
import tt.hashtranslator.service.HashService;

import static java.lang.String.format;

/**
 * Implementation of {@link ApplicationService}.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final HashService hashService;

    @Transactional
    @Override
    public String process(ApplicationDto applicationDto) {
        log.debug("Start processing an application...");
        Application application = new Application();
        application.setHashes(applicationDto.getHashes());
        Application savedApplication = applicationRepository.save(application);
        hashService.decode(savedApplication);
        return format("Application[id: %s] has been accepted", savedApplication.getId());
    }

    @Override
    public Application getById(String id) {
        return applicationRepository.findById(id)
                .orElseThrow(() -> new CommonException(
                        format("Application[id: %s] not found", id), HttpStatus.NOT_FOUND.value()));
    }
}
