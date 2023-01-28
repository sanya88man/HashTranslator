package tt.authorization.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tt.authorization.entity.User;
import tt.authorization.exception.CommonException;
import tt.authorization.repository.UserRepository;

import javax.annotation.PostConstruct;

import static java.util.Collections.singletonList;

/**
 * Implementation of {@link UserDetailsService}.
 */
@Slf4j
@Service("userDetailsService")
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        log.debug("Start checking if user with username {} exists", username);
        User user = userRepository.findUserByEmail(username)
                .orElseThrow(() -> new CommonException(
                        String.format("User with username %s not found", username), HttpStatus.NOT_FOUND.value()));
        return buildUserDetails(user);
    }

    private UserDetails buildUserDetails(User user) {
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), singletonList(new SimpleGrantedAuthority(user.getRole().name())));
    }
}
