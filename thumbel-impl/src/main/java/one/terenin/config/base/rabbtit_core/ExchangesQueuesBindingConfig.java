package one.terenin.config.base.rabbtit_core;

import lombok.RequiredArgsConstructor;
import one.terenin.config.base.propertysource.RabbitMQPropertySource;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ExchangesQueuesBindingConfig {

    private final RabbitMQPropertySource rabbitMQPropertySource;

    @Bean
    public Queue gmailQueue(){
        return QueueBuilder.durable(rabbitMQPropertySource.getGmailQueue())
                .withArgument("x-dead-letter-exchange", rabbitMQPropertySource.getDlqExchangeName())
                .withArgument("x-dead-letter-queue", rabbitMQPropertySource.getDqlQueueName())
                .build();
    }

    @Bean
    public Queue yandexQueue(){
        return QueueBuilder.durable(rabbitMQPropertySource.getYandexQueue())
                .withArgument("x-dead-letter-exchange", rabbitMQPropertySource.getDlqExchangeName())
                .withArgument("x-dead-letter-queue", rabbitMQPropertySource.getDqlQueueName())
                .build();
    }

    @Bean
    public Queue mailQueue(){
        return QueueBuilder.durable(rabbitMQPropertySource.getMailQueue())
                .withArgument("x-dead-letter-exchange", rabbitMQPropertySource.getDlqExchangeName())
                .withArgument("x-dead-letter-queue", rabbitMQPropertySource.getDqlQueueName())
                .build();
    }

    @Bean
    public Exchange exchange(){
        return ExchangeBuilder.topicExchange(rabbitMQPropertySource.getExchange()).build();
    }

    @Bean
    public Binding toGmail(){
        return BindingBuilder
                .bind(gmailQueue())
                .to(exchange())
                .with("*." + rabbitMQPropertySource.getGmailRoutingKey()).noargs();
    }

    @Bean
    public Binding toYandex(){
        return BindingBuilder
                .bind(yandexQueue())
                .to(exchange())
                .with("*." + rabbitMQPropertySource.getYandexRoutingKey()).noargs();
    }

    @Bean
    public Binding toMail(){
        return BindingBuilder
                .bind(mailQueue())
                .to(exchange())
                .with("*." + rabbitMQPropertySource.getMailRoutingKey()).noargs();
    }

}
