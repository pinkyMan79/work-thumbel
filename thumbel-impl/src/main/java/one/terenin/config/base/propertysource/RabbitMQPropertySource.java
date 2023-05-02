package one.terenin.config.base.propertysource;

import lombok.Data;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@Component
@PropertySource("classpath:rabbit.yaml")
public class RabbitMQPropertySource {

    private String username;
    private String password;

}
