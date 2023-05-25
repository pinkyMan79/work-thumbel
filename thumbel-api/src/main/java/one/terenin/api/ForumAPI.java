package one.terenin.api;

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

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    void createForum(@RequestBody ForumRequest request);

    @GetMapping("/find/{title}")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    ResponseEntity<List<ForumResponse>> getForum(@PathVariable("title") String title);

    //@MessageMapping("/send")
    @PostMapping("/send")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    ResponseEntity<ForumResponse> sendMessage(@RequestBody MessageRequest request);

    @GetMapping
    @PreAuthorize("hasAuthority('USER')")
    ResponseEntity<Integer> getCountOfMessages(String login, UUID forumId);

}
