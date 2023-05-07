package one.terenin.api;

import one.terenin.dto.user.UserLoginRequest;
import one.terenin.dto.user.UserRequest;
import one.terenin.dto.user.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/user")
@CrossOrigin(origins = "*", maxAge = 3600)
public interface UserAPI {

    @PostMapping("/register")
    ResponseEntity<UserResponse> register(@RequestBody UserRequest request);

    @GetMapping("/login")
    ResponseEntity<UserResponse> login(@RequestBody UserLoginRequest request);

    @PutMapping("/subscribe/{sub-login}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    void subscribe(@PathVariable("sub-login") String login);

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<List<UserResponse>> getAllUsers();

    // load/upload file + create forum + message sending


}
