package one.terenin.controller.core;

import one.terenin.api.UserAPI;
import one.terenin.dto.user.UserLoginRequest;
import one.terenin.dto.user.UserRequest;
import one.terenin.dto.user.UserResponse;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController implements UserAPI {

    @Override
    public UserResponse register(UserRequest request) {
        return null;
    }

    @Override
    public UserResponse login(UserLoginRequest request) {
        return null;
    }

    @Override
    public void subscribe(String login) {

    }

    @Override
    public List<UserResponse> getAllUsers() {
        return null;
    }
}
