package one.terenin.service.impl.util.mapper;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import one.terenin.dto.communication.ForumRequest;
import one.terenin.dto.communication.ForumResponse;
import one.terenin.dto.communication.common.MessageRequest;
import one.terenin.dto.communication.common.MessageResponse;
import one.terenin.entity.ForumEntity;
import one.terenin.entity.MessageEntity;
import one.terenin.repository.ForumRepository;
import org.hibernate.service.spi.InjectService;
import org.springframework.context.annotation.Scope;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class MessageMapper {

    //private ForumRepository repository;

    public Function<MessageRequest, MessageEntity> fromRequestToEntity = req -> {
        return new MessageEntity(
                req.getContent(),
                null,
                req.getSenderLogin()
        );
    };

    public Function<MessageEntity, MessageResponse> fromEntityToResponse = ent -> {
        return MessageResponse.builder()
                .id(ent.getId())
                .content(ent.getContent())
                .date(ent.getCreatedDate())
                .forumId(ent.getForum().getId())
                .senderLogin(ent.getSenderLogin())
                .build();
    };

}
