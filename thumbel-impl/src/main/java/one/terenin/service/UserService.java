package one.terenin.service;


import one.terenin.dto.user.UserLoginRequest;
import one.terenin.dto.user.UserRequest;
import one.terenin.dto.user.UserResponse;

public interface UserService {

    UserResponse doRegister(UserRequest request);

    UserResponse doLogin(UserLoginRequest loginRequest);

    void doSubscribe(String subcribeToLogin);


}
