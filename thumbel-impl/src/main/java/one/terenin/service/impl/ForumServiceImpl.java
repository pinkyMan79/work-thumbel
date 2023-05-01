package one.terenin.service.impl;

import lombok.RequiredArgsConstructor;
import one.terenin.dto.communication.ForumRequest;
import one.terenin.dto.communication.ForumResponse;
import one.terenin.dto.communication.common.MessageRequest;
import one.terenin.entity.ForumEntity;
import one.terenin.repository.ForumRepository;
import one.terenin.service.ForumService;
import one.terenin.service.impl.util.mapper.ForumMapper;
import one.terenin.service.impl.util.mapper.MessageMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ForumServiceImpl implements ForumService {

    private final ForumRepository repository;
    private final ForumMapper mapper;
    private final MessageMapper msgMapper;

    @Override
    public void createForum(ForumRequest request) {
        repository.save(mapper.fromRequestToEntity.apply(request));
    }

    @Override
    public ForumResponse getForum(String title) {
        return mapper.fromEntityToResponse.apply(repository.findForumEntityByTitle(title));
    }

    @Override
    public ForumResponse sendMessage(MessageRequest request) {
        ForumEntity refreshOnPage = repository.findById(request.getForumId()).orElseThrow();
        refreshOnPage.getMessages().add(msgMapper.fromRequestToEntity.apply(request));
        return mapper.fromEntityToResponse.apply(refreshOnPage);
    }
}
