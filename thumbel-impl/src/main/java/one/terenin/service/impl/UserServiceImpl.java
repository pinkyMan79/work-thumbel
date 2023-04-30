package one.terenin.service.impl;

import lombok.RequiredArgsConstructor;
import one.terenin.dto.user.UserLoginRequest;
import one.terenin.dto.user.UserRequest;
import one.terenin.dto.user.UserResponse;
import one.terenin.repository.UserRepository;
import one.terenin.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    @Override
    public void doRegister(UserRequest request) {

    }

    @Override
    public UserResponse doLogin(UserLoginRequest loginRequest) {
        return null;
    }

    @Override
    public void doSubscribe(String subcribeToLogin) {

    }
}
