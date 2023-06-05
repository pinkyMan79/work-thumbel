package one.terenin.test_config;

import one.terenin.repository.UserRepository;
import one.terenin.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.mockito.Mockito.mock;

@Configuration
public class TestConfig {

    @Bean
    public UserService ser(){
        return mock(UserService.class);
    }

    @Bean
    public UserRepository repo(){
        return mock(UserRepository.class);
    }

}
