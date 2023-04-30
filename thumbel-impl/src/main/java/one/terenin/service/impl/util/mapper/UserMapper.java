package one.terenin.service.impl.util.mapper;

import lombok.RequiredArgsConstructor;
import one.terenin.dto.user.UserRequest;
import one.terenin.dto.user.UserResponse;
import one.terenin.entity.UserEntity;
import one.terenin.entity.common.Role;
import one.terenin.entity.common.State;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder encoder;

    // on generation moment have no files/friends
    public Function<UserRequest, UserEntity> fromRequestToEntity = req -> {
        return new UserEntity(
                req.getLogin(),
                encoder.encode(req.getPassword()),
                req.getPhoto(),
                req.getBiography(),
                null,
                null,
                req.getPosition(),
                State.NOT_BANNED,
                Role.USER
        );
    };

    public Function<UserEntity, UserResponse> fromEntityToResponse = ent -> {
        return UserResponse.builder()
                .biography(ent.getBiography())
                .createdDate(ent.getCreatedDate())
                .id(ent.getId())
                .login(ent.getLogin())
                .photo(ent.getPhoto())
                .position(ent.getPosition())
                .build();
    };

}
