package one.terenin.config.base.propertysource;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@Component
@PropertySource("classpath:/app.yaml")
public class RabbitMQPropertySource {

    // connection
    private String username;
    private String password;
    // base
    private String exchange;
    private String gmailQueue;
    private String gmailRoutingKey;
    private String yandexQueue;
    private String yandexRoutingKey;
    private String mailQueue;
    private String mailRoutingKey;
    // addition
    private String dqlQueueName;
    private String dlqExchangeName;

    public RabbitMQPropertySource(@Value("${rabbit.username}") String username,
                                  @Value("${rabbit.password}") String password,
                                  @Value("${rabbit.exchange}") String exchange,
                                  @Value("${rabbit.gmail.queue}") String gmailQueue,
                                  @Value("${rabbit.gmail.binding}") String gmailRoutingKey,
                                  @Value("${rabbit.yandex.queue}") String yandexQueue,
                                  @Value("${rabbit.yandex.binding}") String yandexRoutingKey,
                                  @Value("${rabbit.mail.queue}") String mailQueue,
                                  @Value("${rabbit.mail.binding}") String mailRoutingKey,
                                  @Value("${rabbit.dqlQueueName}") String dqlQueueName,
                                  @Value("${rabbit.dlqExchangeName}") String dlqExchangeName) {
        this.username = username;
        this.password = password;
        this.exchange = exchange;
        this.gmailQueue = gmailQueue;
        this.gmailRoutingKey = gmailRoutingKey;
        this.yandexQueue = yandexQueue;
        this.yandexRoutingKey = yandexRoutingKey;
        this.mailQueue = mailQueue;
        this.mailRoutingKey = mailRoutingKey;
        this.dqlQueueName = dqlQueueName;
        this.dlqExchangeName = dlqExchangeName;
    }
}
