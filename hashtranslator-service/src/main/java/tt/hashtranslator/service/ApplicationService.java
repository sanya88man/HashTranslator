package tt.hashtranslator.service;

import tt.hashtranslator.dto.ApplicationDto;
import tt.hashtranslator.entity.Application;

/**
 * Service for manipulating application.
 *
 * @see Application
 */
public interface ApplicationService {
    /**
     * Processes incoming application.
     *
     * @param applicationDto {@link ApplicationDto}
     * @return application id
     */
    String process(ApplicationDto applicationDto);

    /**
     * Gets application by id.
     *
     * @param id application id
     * @return {@link Application}
     */
    Application getById(String id);
}
