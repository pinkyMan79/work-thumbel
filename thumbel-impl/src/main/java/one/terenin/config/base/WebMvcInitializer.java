package one.terenin.config.base;

import one.terenin.service.impl.util.mapper.FileMapper;
import one.terenin.service.impl.util.mapper.ForumMapper;
import one.terenin.service.impl.util.mapper.MessageMapper;
import one.terenin.service.impl.util.mapper.UserMapper;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ComponentScan(basePackages = "one.terenin"
     /*   , excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = ForumMapper.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = MessageMapper.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = UserMapper.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = FileMapper.class)
    }*/
)
@EnableWebMvc
@EnableCaching
public class WebMvcInitializer implements WebMvcConfigurer {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new MappingJackson2HttpMessageConverter());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/")
                .setCachePeriod(31556926);
    }

    @Bean
    public InternalResourceViewResolver viewResolver(){
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jspx");
        viewResolver.setRequestContextAttribute("requestContext");
        return viewResolver;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("/fragments/index");
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    public RestTemplate restTemplate(){
        RestTemplate template = new RestTemplate();
        List<HttpMessageConverter<?>>  converters = new ArrayList<>();
        converters.add(new MappingJackson2HttpMessageConverter());
        template.setMessageConverters(converters);
        return template;
    }

    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

}
