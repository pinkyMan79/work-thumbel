package one.terenin.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import one.terenin.dto.user.UserLoginRequest;
import one.terenin.dto.user.UserRequest;
import one.terenin.dto.user.UserResponse;
import one.terenin.entity.UserEntity;
import one.terenin.entity.common.Role;
import one.terenin.repository.UserRepository;
import one.terenin.security.details.UserDetailsBase;
import one.terenin.dto.security.JwtResponse;
import one.terenin.security.token.filter.common.SecurityConstants;
import one.terenin.security.token.filter.common.util.JwtUtils;
import one.terenin.service.UserService;
import one.terenin.service.impl.util.mapper.UserMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final JwtUtils utils;
    private final UserMapper mapper;
    private final AuthenticationManager manager;

    @Override
    public UserResponse doRegister(UserRequest request) {

        String login = request.getLogin();
        if (repository.existsByLogin(login)){
            return UserResponse.builder().login("login_done").build();
        }
        UserEntity entity = mapper.fromRequestToEntity.apply(request);
        repository.save(mapper.fromRequestToEntity.apply(request));
        entity.setRole(Role.USER);
        return mapper.fromEntityToResponse.apply(entity);
    }

    @Override
    public JwtResponse doLogin(UserLoginRequest loginRequest) {
       Authentication authentication = manager.authenticate(
               new UsernamePasswordAuthenticationToken(loginRequest.getLogin(),
                       loginRequest.getPassword())
       );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = utils.generateJwToken(authentication);
        UserDetailsBase userDetails = (UserDetailsBase) authentication.getPrincipal();
        return new JwtResponse(token,
                SecurityConstants.TOKEN_PREFIX,
                userDetails.getEntity().getId(),
                userDetails.getUsername(),
                Collections.singleton(userDetails.getEntity().getRole().toString()));
    }


    /**@apiNote
     * Логика такая - берем пользователя из {@link SecurityContext}
     * Берём при помощи такого объекта аутентификации как {@link UsernamePasswordAuthenticationToken}
     * он там уже заведомо лежит так как пользователь залогинен, выдача объекта аутентификации происходит в методе
     * doLogin выше, везде где нам нужен пользователь используем Context
     * */
    @Override
    public void doSubscribe(String subscribeToLogin) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info(authentication.getName() + "  " + authentication.getPrincipal());
        log.info("{}{}", "get authentication with: ", authentication.getName());
        UserEntity sessionUser = repository.findUserEntityByLogin(((UserDetailsBase)
                (authentication.getPrincipal())).getUsername());
        UserEntity subscribeTo = repository.findUserEntityByLogin(subscribeToLogin);
        sessionUser.getFriends().add(subscribeTo);
        repository.save(sessionUser);
    }

    @Override
    public List<UserResponse> showFriends(String login) {
        List<UserEntity> allSubscribersByLogin = repository.findAllSubscribersByLogin(login);
        List<UserResponse> responses = new ArrayList<>();
        for (UserEntity entity : allSubscribersByLogin) {
            responses.add(mapper.fromEntityToResponse.apply(entity));
        }
        return responses;
    }
}
