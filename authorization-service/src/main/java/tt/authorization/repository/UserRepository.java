package tt.authorization.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tt.authorization.entity.User;

import java.util.Optional;

/**
 * Repository for {@link User} entity.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds {@link User} by email.
     *
     * @param email user email
     * @return {@link Optional<User>}
     */
    Optional<User> findUserByEmail(String email);
}
