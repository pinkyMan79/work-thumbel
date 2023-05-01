package one.terenin.service.impl;

import lombok.RequiredArgsConstructor;
import one.terenin.dto.user.UserLoginRequest;
import one.terenin.dto.user.UserRequest;
import one.terenin.dto.user.UserResponse;
import one.terenin.repository.UserRepository;
import one.terenin.service.UserService;
import one.terenin.service.impl.util.mapper.UserMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    @Override
    public void doRegister(UserRequest request) {
        repository.save(mapper.fromRequestToEntity.apply(request));
    }

    @Override
    public UserResponse doLogin(UserLoginRequest loginRequest) {
       return mapper
               .fromEntityToResponse
               .apply(repository
                       .findUserEntityByLoginAndHashPassword(
                               loginRequest.getLogin(),
                               loginRequest.getPassword()));
    }

    @Override
    public void doSubscribe(String subscribeToLogin) {
        // session user do subscription to someone by user
        // session use like SecurityContext
    }
}
