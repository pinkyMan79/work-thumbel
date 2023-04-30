package one.terenin.service.impl;

import lombok.RequiredArgsConstructor;
import one.terenin.dto.communication.ForumRequest;
import one.terenin.dto.communication.ForumResponse;
import one.terenin.dto.communication.common.MessageRequest;
import one.terenin.repository.ForumRepository;
import one.terenin.service.ForumService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ForumServiceImpl implements ForumService {

    private final ForumRepository repository;

    @Override
    public void createForum(ForumRequest request) {}

    @Override
    public ForumResponse getForum(String title) {
        return null;
    }

    @Override
    public ForumResponse sendMessage(MessageRequest request) {
        return null;
    }
}
