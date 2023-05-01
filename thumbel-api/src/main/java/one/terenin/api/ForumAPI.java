package one.terenin.api;

import one.terenin.dto.communication.ForumRequest;
import one.terenin.dto.communication.ForumResponse;
import one.terenin.dto.communication.common.MessageRequest;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/forum")
public interface ForumAPI {

    @PostMapping("/create")
    void createForum(ForumRequest request);

    @GetMapping("/find/{title}")
    ForumResponse getForum(@PathVariable("title") String title);

    @PostMapping("/send")
    ForumResponse sendMessage(@RequestBody MessageRequest request);

}
