package one.terenin.api;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import one.terenin.dto.communication.ForumRequest;
import one.terenin.dto.communication.ForumResponse;
import one.terenin.dto.communication.common.MessageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/forum")
@CrossOrigin(origins = "*", maxAge = 3600)
public interface ForumAPI {

    @ApiOperation(value = "Создание форума", nickname = "crt-forum")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Создал", response = Void.class),
            @ApiResponse(code = 400, message = "Не создал")})
    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    void createForum(@RequestBody ForumRequest request);

    @ApiOperation(value = "Найти форумы по названиям", nickname = "get-forum")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Нашёлся", response = Void.class),
            @ApiResponse(code = 400, message = "Не нашёлся")})
    @GetMapping("/find/{title}")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    ResponseEntity<List<ForumResponse>> getForum(@PathVariable("title") String title);

    @ApiOperation(value = "Отправка сообщения", nickname = "send-msg")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Отправил", response = Void.class),
            @ApiResponse(code = 400, message = "Не отправил")})
    @PostMapping("/send")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    ResponseEntity<ForumResponse> sendMessage(@RequestBody MessageRequest request);

    @ApiOperation(value = "Получение количества сообщений на форуме от пользователя", nickname = "get-count")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Посчитал", response = Void.class),
            @ApiResponse(code = 400, message = "Не посчитал")})
    @GetMapping
    @PreAuthorize("hasAuthority('USER')")
    ResponseEntity<Integer> getCountOfMessages(String login, UUID forumId);

}
