package one.terenin.api;

import one.terenin.dto.user.UserLoginRequest;
import one.terenin.dto.user.UserRequest;
import one.terenin.dto.user.UserResponse;
import org.springframework.amqp.core.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/user")
@CrossOrigin(origins = "*", maxAge = 3600)
public interface UserAPI {

    @PostMapping("/register")
    ResponseEntity<UserResponse> register(@RequestBody UserRequest request);

    @PostMapping("/login")
    ResponseEntity<UserResponse> login(@RequestBody UserLoginRequest request);

    @PutMapping("/subscribe/{sub-login}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    void subscribe(@PathVariable("sub-login") String login);

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<List<UserResponse>> getAllUsers();

    /** @apiNote
     * После того как будет сделана попытка регистрации, автоматически пользователю отправляем e-mail в котором будет
     * ссылка на форму логина, но в форму логина, и уже прошедшая регистрация будет храниться в бд, а форма логина
     * просто подтвердит валидность e-mail и сгенерит JWT токен
     * */
    @PostMapping("/email")
    @PreAuthorize("permitAll()")
    void sendEmail(@RequestBody Message message);

    // load/upload file + create forum + message sending


}
