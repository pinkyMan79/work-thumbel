package one.terenin.api;

import one.terenin.dto.communication.ForumRequest;
import one.terenin.dto.communication.ForumResponse;
import one.terenin.dto.communication.common.MessageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/forum")
@CrossOrigin(origins = "*", maxAge = 3600)
public interface ForumAPI {


    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    void createForum(@RequestBody ForumRequest request);

    @GetMapping("/find/{title}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    ResponseEntity<ForumResponse> getForum(@PathVariable("title") String title);

    @MessageMapping("/send")
    @PostMapping("/send")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    ResponseEntity<ForumResponse> sendMessage(@RequestBody MessageRequest request);

}
