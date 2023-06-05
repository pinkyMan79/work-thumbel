package one.terenin.config.base;

import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;

import one.terenin.config.base.propertysource.RabbitMQPropertySource;
import one.terenin.config.base.rabbtit_core.RabbitListenerConfig;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.OptionalValidatorFactoryBean;

@Configuration
@Import({RabbitListenerConfig.class})
@RequiredArgsConstructor
public class RabbitMQConfig {

    private final RabbitMQPropertySource propertySource;

    @Bean
    public ConnectionFactory connectionFactory(){
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
        cachingConnectionFactory.setUsername(propertySource.getUsername());
        cachingConnectionFactory.setPassword(propertySource.getPassword());
        cachingConnectionFactory.setHost("localhost");
        cachingConnectionFactory.setPort(5671);
        cachingConnectionFactory.setVirtualHost("/");
        cachingConnectionFactory.setConnectionTimeout(100);
        //cachingConnectionFactory.setConnectionCacheSize(10);
        cachingConnectionFactory.setChannelCacheSize(10);
        cachingConnectionFactory.setCacheMode(CachingConnectionFactory.CacheMode.CHANNEL);
        return cachingConnectionFactory;
    }

    @Bean
    public AmqpAdmin rabbitAdmin(@Qualifier("connectionFactory") ConnectionFactory connectionFactory){
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public RabbitTemplate template(@Qualifier("connectionFactory") ConnectionFactory connectionFactory){
        return new RabbitTemplate(connectionFactory);
    }

    @Bean
    public Connection connection(RabbitTemplate template){
        return template.getConnectionFactory().createConnection();
    }

    @Bean
    public Channel channel(Connection connection){
        return connection.createChannel(false);
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setAutoStartup(false);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        return factory;
    }

    @Bean
    public RetryTemplate retryTemplate(){
        FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
        backOffPolicy.setBackOffPeriod(50000);
        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(2);
        RetryTemplate retryTemplate = new RetryTemplate();
        retryTemplate.setBackOffPolicy(backOffPolicy);
        retryTemplate.setRetryPolicy(retryPolicy);
        return retryTemplate;
    }

    @Bean
    public void configureRabbitTemplate() {
        template(connectionFactory()).setMessageConverter(new Jackson2JsonMessageConverter());
    }

    /*@Bean
    public Validator amqpValidator() {
        return new OptionalValidatorFactoryBean();
    }*/

}
