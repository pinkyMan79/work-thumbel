package one.terenin.config.base.propertysource;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@Component
@PropertySource("classpath:app.yaml")
public class RabbitMQPropertySource {

    private String username;
    private String password;

    public RabbitMQPropertySource(@Value("${rabbit.username}") String username,
                                  @Value("${rabbit.password}") String password) {
        this.username = username;
        this.password = password;
    }
}
