package one.terenin.service.impl;

import one.terenin.dto.user.UserLoginRequest;
import one.terenin.dto.user.UserRequest;
import one.terenin.dto.user.UserResponse;
import one.terenin.repository.UserRepository;
import one.terenin.service.UserService;
import one.terenin.test_config.TestConfig;
import one.terenin.service.impl.util.mapper.UserMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class, loader = AnnotationConfigContextLoader.class)
class UserServiceImplTest {

    @Autowired
    @Qualifier("ser")
    private UserService service;

    @Autowired
    @Qualifier("repo")
    private UserRepository repository;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private UserMapper mapper;

    private final static String email = "danila@gmail.com";
    private static final UserLoginRequest request = mock(UserLoginRequest.class);

    @BeforeAll
    public static void setUp(){
        when(request.getLogin()).thenReturn(email);
    }

    @Test
    void doRegister() {
        service.doRegister(new UserRequest());
    }

    @Test
    void doLogin() {
        UserResponse response = service.doLogin(request);
        Assertions.assertNull(response);
    }

    @Test
    void doSubscribe() {
    }
}