package one.terenin.service;


import one.terenin.dto.user.UserLoginRequest;
import one.terenin.dto.user.UserRequest;
import one.terenin.dto.user.UserResponse;
import one.terenin.dto.security.JwtResponse;

import javax.servlet.http.HttpServletRequest;
import java.net.http.HttpRequest;

public interface UserService {

    UserResponse doRegister(UserRequest request);

    JwtResponse doLogin(UserLoginRequest loginRequest);

    void doSubscribe(String subcribeToLogin);


}
