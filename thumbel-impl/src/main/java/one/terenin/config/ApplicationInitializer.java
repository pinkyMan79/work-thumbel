package one.terenin.config;

import one.terenin.config.base.*;
import one.terenin.security.SecurityConfig;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;


public class ApplicationInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();

        context.register(YAMLConfigurer.class);
        context.register(DataBaseConfig.class);
        context.register(WebMvcInitializer.class);
        context.register(SecurityConfig.class);
        context.register(RabbitMQConfig.class);
        context.register(RedisConfig.class);
        context.register(WebSocketConfig.class);
        context.register(SwaggerConfig.class);

        servletContext.addListener(new ContextLoaderListener(context));

        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher",
                new DispatcherServlet(context));
        dispatcher.setMultipartConfig(
                new MultipartConfigElement(null, 5000000, 5000000, 0));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");

    }

}
