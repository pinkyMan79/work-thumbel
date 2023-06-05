package one.terenin.service.impl.util.mapper;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import one.terenin.dto.communication.ForumRequest;
import one.terenin.dto.communication.ForumResponse;
import one.terenin.dto.file.FileRequest;
import one.terenin.dto.file.FileResponse;
import one.terenin.entity.FileEntity;
import one.terenin.entity.ForumEntity;
import org.springframework.stereotype.Component;

import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@AllArgsConstructor
public class ForumMapper {

    private MessageMapper messageMapper;

    public Function<ForumRequest, ForumEntity> fromRequestToEntity = req -> {
        return new ForumEntity(
                req.getTitle(),
                req.getDescription(),
                null
        );
    };

    public Function<ForumEntity, ForumResponse> fromEntityToResponse = ent -> {
        return ForumResponse.builder()
                .title(ent.getTitle())
                .description(ent.getDescription())
                .messages(ent.getMessages()
                        .stream()
                        .map(enty -> messageMapper.fromEntityToResponse.apply(enty)).collect(Collectors.toSet()))
                .id(ent.getId())
                .build();
    };

}
