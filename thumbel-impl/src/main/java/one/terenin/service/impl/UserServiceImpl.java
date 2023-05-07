package one.terenin.service.impl;

import lombok.RequiredArgsConstructor;
import one.terenin.dto.user.UserLoginRequest;
import one.terenin.dto.user.UserRequest;
import one.terenin.dto.user.UserResponse;
import one.terenin.entity.UserEntity;
import one.terenin.entity.common.Role;
import one.terenin.repository.UserRepository;
import one.terenin.security.details.UserDetailsBase;
import one.terenin.security.token.filter.common.util.JwtUtils;
import one.terenin.service.UserService;
import one.terenin.service.impl.util.mapper.UserMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

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
    public UserResponse doLogin(UserLoginRequest loginRequest) {
       Authentication authentication = manager.authenticate(
               new UsernamePasswordAuthenticationToken(loginRequest.getLogin(), loginRequest.getPassword())
       );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = utils.generateJwToken(authentication);
        UserDetailsBase userDetails = (UserDetailsBase) authentication.getPrincipal();
        List<String> userRoles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList();
        return mapper.fromEntityToResponse.apply(userDetails.getEntity());
    }

    @Override
    public void doSubscribe(String subscribeToLogin) {
        // session user do subscription to someone by user
        // session use like SecurityContext
    }
}
