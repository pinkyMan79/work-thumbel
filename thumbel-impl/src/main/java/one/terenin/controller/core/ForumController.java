package one.terenin.controller.core;

import lombok.RequiredArgsConstructor;
import one.terenin.api.ForumAPI;
import one.terenin.dto.communication.ForumRequest;
import one.terenin.dto.communication.ForumResponse;
import one.terenin.dto.communication.common.MessageRequest;
import one.terenin.service.ForumService;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.StreamingHttpOutputMessage;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class ForumController implements ForumAPI {

    private final ForumService forumService;

    @Override
    public void createForum(ForumRequest request) {
        forumService.createForum(request);
    }

    @Override
    public ResponseEntity<List<ForumResponse>> getForum(String title) {
        return ResponseEntity.ok(forumService.getForums(title, Pageable.ofSize(10)));
    }

    @Override
    public ResponseEntity<ForumResponse> sendMessage(MessageRequest request) {
        return ResponseEntity.ok(forumService.sendMessage(request));
    }
}
