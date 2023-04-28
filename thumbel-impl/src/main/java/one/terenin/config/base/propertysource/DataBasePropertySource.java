package one.terenin.config.base.propertysource;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@Component
@PropertySource("classpath:/app.yaml")
public class DataBasePropertySource {

    private final String url;
    private final String username;
    private final String password;
    private final int poolSize;
    private final String driverClassname;

    public DataBasePropertySource(@Value("${db.url}") String url,
                                  @Value("${db.username}") String username,
                                  @Value("${db.password}") String password,
                                  @Value("${db.hikari.max-pool-size}") int poolSize,
                                  @Value("${db.driver.classname}") String driverClassName) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.poolSize = poolSize;
        this.driverClassname = driverClassName;
    }

}
