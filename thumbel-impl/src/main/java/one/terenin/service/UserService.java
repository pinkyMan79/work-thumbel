package one.terenin.service;


import one.terenin.dto.user.UserLoginRequest;
import one.terenin.dto.user.UserRequest;
import one.terenin.dto.user.UserResponse;
import one.terenin.security.token.filter.common.JwtResponse;

public interface UserService {

    UserResponse doRegister(UserRequest request);

    JwtResponse doLogin(UserLoginRequest loginRequest);

    void doSubscribe(String subcribeToLogin);


}
