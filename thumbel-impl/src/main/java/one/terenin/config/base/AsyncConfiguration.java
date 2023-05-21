/*
package one.terenin.config.base;

import java.lang.reflect.Method;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

@Configuration
@EnableAsync
public class AsyncConfiguration extends AsyncConfigurerSupport {

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(50);
        executor.setThreadNamePrefix("AsyncTaskThread::");
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.initialize();
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler  getAsyncUncaughtExceptionHandler() {
            return new AsyncUncaughtExceptionHandler() {
                @Override
                public void handleUncaughtException(Throwable ex,
                                                    Method method, Object... params) {

                    ex.printStackTrace();
                }
            };
}}*/
