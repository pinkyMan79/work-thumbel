package one.terenin.service;

import one.terenin.dto.communication.ForumRequest;
import one.terenin.dto.communication.ForumResponse;
import one.terenin.dto.communication.common.MessageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ForumService {

    void createForum(ForumRequest request);

    List<ForumResponse> getForums(String title, Pageable pageable);

    ForumResponse sendMessage(MessageRequest request);

    Integer getCount(String login, UUID forumId);

}
