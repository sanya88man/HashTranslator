package tt.hashtranslator.service;

import tt.hashtranslator.entity.Application;

/**
 * Service for manipulating hashes.
 */
public interface HashService {

    /**
     * Decodes application's hashes.
     *
     * @param application {@link Application}
     */
    void decode(Application application);
}
