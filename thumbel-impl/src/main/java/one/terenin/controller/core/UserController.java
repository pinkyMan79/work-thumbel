package one.terenin.controller.core;

import lombok.RequiredArgsConstructor;
import one.terenin.api.UserAPI;
import one.terenin.dto.user.UserLoginRequest;
import one.terenin.dto.user.UserRequest;
import one.terenin.dto.user.UserResponse;
import one.terenin.dto.security.JwtResponse;
import one.terenin.service.UserService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController implements UserAPI {

    private final UserService service;
    private final RabbitTemplate template;

    @Override
    public ResponseEntity<UserResponse> register(UserRequest request) {
        UserResponse response = service.doRegister(request);
        template.send(request.getEmail(), new Message(request.getEmail().getBytes(StandardCharsets.UTF_8)));
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
    public void sendEmail(String email) {
        // useless
        template.send(email, new Message(email.getBytes(StandardCharsets.UTF_8)));
    }
}
