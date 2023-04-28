package one.terenin.api;

import one.terenin.dto.user.UserLoginRequest;
import one.terenin.dto.user.UserRequest;
import one.terenin.dto.user.UserResponse;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user")
public interface UserAPI {

    @PostMapping("/register")
    UserResponse register(@RequestBody UserRequest request);

    @GetMapping("/login")
    UserResponse login(@RequestBody UserLoginRequest request);

    @PutMapping("/subscribe/{sub-login}")
    void subscribe(@PathVariable("sub-login") String login);

    // load/upload file + create forum + message sending


}
