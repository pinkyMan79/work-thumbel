package one.terenin.service.impl.util.mapper;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import one.terenin.dto.file.FileRequest;
import one.terenin.dto.file.FileResponse;
import one.terenin.dto.user.UserRequest;
import one.terenin.dto.user.UserResponse;
import one.terenin.dto.util.Position;
import one.terenin.entity.FileEntity;
import one.terenin.entity.UserEntity;
import one.terenin.entity.common.Role;
import one.terenin.entity.common.State;
import one.terenin.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
@AllArgsConstructor
public class FileMapper {

    private UserRepository repository;

    // on generation moment have no files/friends
    public Function<FileRequest, FileEntity> fromRequestToEntity = req -> {
        return new FileEntity(
                req.getFileName(),
                req.getFileLocation(),
                req.getData(),
                repository.findById(req.getMaintainer()).get()
        );
    };

    public Function<FileEntity, FileResponse> fromEntityToResponse = ent -> {
        return FileResponse.builder()
                .data(ent.getData())
                .fileName(ent.getFileName())
                .id(ent.getId())
                .maintainer(ent.getMaintainer().getId())
                .build();
    };

}
