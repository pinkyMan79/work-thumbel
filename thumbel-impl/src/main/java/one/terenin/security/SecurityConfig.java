package one.terenin.security;

import lombok.SneakyThrows;
import net.bytebuddy.utility.nullability.NeverNull;
import one.terenin.security.details.UserDetailsServiceBase;
import one.terenin.security.token.filter.actual.AuthTokenFilter;
import one.terenin.security.token.filter.actual.AuthenticationEntryPointExceptionInterceptorJwt;
import one.terenin.security.token.filter.common.util.JwtUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder encoder;
    private final UserDetailsServiceBase userService;
    private final DataSource dataSource;

    /**
     * @apiNote
     * Why not a lombok.RequiredArgsConstructor?
     * There is two main problems associated with this:
     * 1. In Spring Security infrastructure already got one of "inMemory" realisation of interface UserDetailsService
     * and that is why we need to qualify our realisation
     * 2. In Documentation for Spring Security v5 it preferred to use constructor with bean qualify
     * */

    public SecurityConfig(PasswordEncoder encoder,
                          @Qualifier("userDetailsServiceBase") UserDetailsServiceBase userService,
                          DataSource dataSource) {
        this.encoder = encoder;
        this.userService = userService;
        this.dataSource = dataSource;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http// -- common configuration
                //add custom JWT filter
                .exceptionHandling()
                .authenticationEntryPoint(exceptionInterceptorJwt())
                .and()
                .addFilterBefore(authTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                .cors()
                .and()
                // сессию отключаем, так как теперь у нас всё хранится в JWT и оттуда мы тянем информацию
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests()
                .antMatchers("/forum/**").permitAll()
                .antMatchers("/user/**").permitAll()
                .antMatchers("/file/**").permitAll()
        ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(encoder).configure(auth);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Bean
    public FilterChainProxy filterChainProxy() {
        List<SecurityFilterChain> securityFilterChains = new ArrayList<>();
        securityFilterChains.add(new DefaultSecurityFilterChain(
                new AntPathRequestMatcher("/admin/**"), authTokenFilter()));
        securityFilterChains.add(new DefaultSecurityFilterChain(
                new AntPathRequestMatcher("/user/**"), authTokenFilter()));
        securityFilterChains.add(new DefaultSecurityFilterChain(
                new AntPathRequestMatcher("/**"), authTokenFilter()));
        return new FilterChainProxy(securityFilterChains);
    }

    @Bean
    public PersistentTokenRepository tokenRepository(){
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        return tokenRepository;
    }

    @Bean
    public AuthTokenFilter authTokenFilter(){
        return new AuthTokenFilter();
    }

    @Bean
    public AuthenticationEntryPointExceptionInterceptorJwt exceptionInterceptorJwt(){
        return new AuthenticationEntryPointExceptionInterceptorJwt();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManager();
    }

}
