package one.terenin.service.impl;

import lombok.RequiredArgsConstructor;
import one.terenin.dto.communication.ForumRequest;
import one.terenin.dto.communication.ForumResponse;
import one.terenin.dto.communication.common.MessageRequest;
import one.terenin.entity.ForumEntity;
import one.terenin.entity.MessageEntity;
import one.terenin.repository.ForumRepository;
import one.terenin.repository.MessageRepository;
import one.terenin.service.ForumService;
import one.terenin.service.impl.util.mapper.ForumMapper;
import one.terenin.service.impl.util.mapper.MessageMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ForumServiceImpl implements ForumService {

    private final ForumRepository forumRepository;
    private final MessageRepository messageRepository;
    private final ForumMapper mapper;
    private final MessageMapper msgMapper;

    @Override
    public void createForum(ForumRequest request) {
        forumRepository.save(mapper.fromRequestToEntity.apply(request));
    }

    @Override
    public List<ForumResponse> getForums(String title, Pageable pageable) {
        List<ForumEntity> forumEntityByTitle = forumRepository.findForumEntityByTitle(title, pageable);
        List<ForumResponse> forumResponseList = new ArrayList<>();
        for (ForumEntity forumEntity: forumEntityByTitle) {
            forumResponseList.add(mapper.fromEntityToResponse.apply(forumEntity));
        }
        return forumResponseList;
    }

    @Override
    public ForumResponse sendMessage(MessageRequest request) {
        ForumEntity refreshOnPage = forumRepository.findById(request.getForumId()).orElseThrow();
        MessageEntity receivedMessage = doSaveMessage(request);
        receivedMessage.setForum(refreshOnPage);
        messageRepository.save(receivedMessage);
        return mapper.fromEntityToResponse.apply(refreshOnPage);
    }

    @Override
    public Integer getCount(String login, UUID forumId) {
        return forumRepository.specialQuery(forumId, login);
    }

    private MessageEntity doSaveMessage(MessageRequest request){
        MessageEntity inputtedMessage = msgMapper.fromRequestToEntity.apply(request);
        MessageEntity save = messageRepository.save(inputtedMessage);
        return save;
    }

}
