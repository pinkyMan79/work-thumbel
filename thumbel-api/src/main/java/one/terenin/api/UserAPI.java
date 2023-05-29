package one.terenin.api;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import one.terenin.dto.security.JwtResponse;
import one.terenin.dto.user.UserLoginRequest;
import one.terenin.dto.user.UserRequest;
import one.terenin.dto.user.UserResponse;
import org.springframework.amqp.core.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/user")
@CrossOrigin(origins = "*", maxAge = 3600)
public interface UserAPI {

    @ApiOperation(value = "Регистрация", nickname = "register")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Зареган", response = Void.class),
            @ApiResponse(code = 400, message = "Не получилось(")})
    @PostMapping("/register")
    ResponseEntity<UserResponse> register(@RequestBody UserRequest request);

    @ApiOperation(value = "Логин", nickname = "login")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Залогинен", response = Void.class),
            @ApiResponse(code = 400, message = "Не получилось(")})
    @PostMapping("/login")
    ResponseEntity<JwtResponse> login(@RequestBody UserLoginRequest request);

    @ApiOperation(value = "Подписка на пользователя", nickname = "subscribe")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Подписался", response = Void.class),
            @ApiResponse(code = 400, message = "Не подписался")})
    @PutMapping("/subscribe/{sub-login}")
    @PreAuthorize("hasAnyAuthority('USER') or hasAuthority('ADMIN')")
    void subscribe(@PathVariable("sub-login") String login);

    @ApiOperation(value = "no-api", nickname = "no-api")
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<List<UserResponse>> getAllUsers();
    /** @apiNote
     * После того как будет сделана попытка регистрации, автоматически пользователю отправляем e-mail в котором будет
     * ссылка на форму логина, но в форму логина, и уже прошедшая регистрация будет храниться в бд, а форма логина
     * просто подтвердит валидность e-mail и сгенерит JWT токен
     * */
    @ApiOperation(value = "no-api", nickname = "no-apii")
    @GetMapping("/restore-password/{email}")
    void sendEmail(@PathVariable("email") String email);

    // load/upload file + create forum + message sending


}
