package one.terenin.service.impl;

import one.terenin.dto.user.UserRequest;
import one.terenin.dto.user.UserResponse;
import one.terenin.entity.UserEntity;
import one.terenin.repository.UserRepository;
import one.terenin.service.UserService;
import one.terenin.service.impl.util.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class UserServiceImplTest {

    @Mock
    private UserRepository repository;

    @Mock
    private UserMapper mapper;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDoRegister() {
        UserRequest request = new UserRequest();
        request.setLogin("test_login");
        UserEntity entity = new UserEntity();
        entity.setLogin("test_login");
        when(repository.existsByLogin(request.getLogin())).thenReturn(false);
        when(mapper.fromRequestToEntity.apply(request)).thenReturn(entity);
        when(repository.save(any(UserEntity.class))).thenReturn(entity);
        when(mapper.fromEntityToResponse.apply(entity)).thenReturn(new UserResponse());

        UserResponse response = userService.doRegister(request);

        assertEquals("test_login", response.getLogin());
    }
}
