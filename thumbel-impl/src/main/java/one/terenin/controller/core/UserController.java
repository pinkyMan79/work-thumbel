package one.terenin.controller.core;

import lombok.RequiredArgsConstructor;
import one.terenin.api.UserAPI;
import one.terenin.dto.user.UserLoginRequest;
import one.terenin.dto.user.UserRequest;
import one.terenin.dto.user.UserResponse;
import one.terenin.dto.security.JwtResponse;
import one.terenin.service.UserService;
import org.springframework.amqp.core.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController implements UserAPI {

    private final UserService service;

    @Override
    public ResponseEntity<UserResponse> register(UserRequest request) {
        UserResponse response = service.doRegister(request);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<JwtResponse> login(UserLoginRequest request) {
        return ResponseEntity.ok(service.doLogin(request));
    }

    @Override
    public void subscribe(String login) {
        service.doSubscribe(login);
    }

    @Override
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return null;
    }

    @Override
    public void sendEmail(Message message) {

    }
}
