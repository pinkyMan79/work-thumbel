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
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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
    public List<ForumResponse> getForums(String title, Pageable pageable) {
        List<ForumEntity> forumEntityByTitle = repository.findForumEntityByTitle(title, pageable);
        List<ForumResponse> forumResponseList = new ArrayList<>();
        for (ForumEntity forumEntity: forumEntityByTitle) {
            forumResponseList.add(mapper.fromEntityToResponse.apply(forumEntity));
        }
        return forumResponseList;
    }

    @Override
    public ForumResponse sendMessage(MessageRequest request) {
        ForumEntity refreshOnPage = repository.findById(request.getForumId()).orElseThrow();
        refreshOnPage.getMessages().add(msgMapper.fromRequestToEntity.apply(request));
        return mapper.fromEntityToResponse.apply(refreshOnPage);
    }
}
