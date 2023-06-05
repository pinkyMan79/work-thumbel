package one.terenin.security.details;

import lombok.RequiredArgsConstructor;
import one.terenin.entity.UserEntity;
import one.terenin.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserDetailsServiceBase implements UserDetailsService {

    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        UserEntity entity = repository.findUserEntityByLogin(login);
        return new UserDetailsBase(entity);
    }
}
