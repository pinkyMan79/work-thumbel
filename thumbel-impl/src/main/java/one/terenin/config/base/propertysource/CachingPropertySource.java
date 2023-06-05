package one.terenin.config.base.propertysource;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class CachingPropertySource {

    private String host;
    private String port;
    private String username;
    private String password;

    public CachingPropertySource(@Value("${caching.host}") String host,
                                 @Value("${caching.port}") String port,
                                 @Value("${caching.username}") String username,
                                 @Value("${caching.password}") String password) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }
}
