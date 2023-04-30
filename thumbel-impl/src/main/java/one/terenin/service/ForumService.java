package one.terenin.service;

import one.terenin.dto.communication.ForumRequest;
import one.terenin.dto.communication.ForumResponse;
import one.terenin.dto.communication.common.MessageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface ForumService {

    void createForum(ForumRequest request);

    ForumResponse getForum(String title);

    ForumResponse sendMessage(MessageRequest request);

}
