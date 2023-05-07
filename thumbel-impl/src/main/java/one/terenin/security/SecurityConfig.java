package one.terenin.security;

import lombok.SneakyThrows;
import net.bytebuddy.utility.nullability.NeverNull;
import one.terenin.security.details.UserDetailsServiceBase;
import one.terenin.security.token.filter.actual.AuthTokenFilter;
import one.terenin.security.token.filter.common.util.JwtUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder encoder;
    private final UserDetailsServiceBase userService;
    private final DataSource dataSource;
    private final JwtUtils utils;

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
                          DataSource dataSource,
                          JwtUtils utils) {
        this.encoder = encoder;
        this.userService = userService;
        this.dataSource = dataSource;
        this.utils = utils;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http// -- common configuration
                //add custom JWT filter
                .addFilter(authTokenFilter())
                .cors()
                .and()
                // сессию отключаем, так как теперь у нас всё хранится в JWT и оттуда мы тянем информацию
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests()
                .antMatchers("/forum/**").permitAll()
                .antMatchers("/user/**").permitAll()
                .antMatchers("/file/**").permitAll()
                .anyRequest().authenticated();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(encoder);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Bean
    public PersistentTokenRepository tokenRepository(){
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        return tokenRepository;
    }

    @SneakyThrows
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http){
        return http.build();
    }

    @Bean
    public AuthTokenFilter authTokenFilter(){
        return new AuthTokenFilter();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
