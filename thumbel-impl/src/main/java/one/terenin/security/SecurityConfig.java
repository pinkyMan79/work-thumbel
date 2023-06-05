package one.terenin.security;

import lombok.Data;
import one.terenin.security.details.UserDetailsServiceBase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.persistence.Persistence;
import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
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
        http.authorizeRequests()
                // -- User base block
                .antMatchers("/user/register/").permitAll()
                .antMatchers("/user/login/").permitAll()
                .antMatchers("/user/subscribe/").authenticated()
                // -- File block
                .antMatchers("/file/upload/").authenticated()
                .antMatchers("/file/download/").authenticated()
                // -- Forum block
                .antMatchers("/forum/create/").hasAuthority("ADMIN")
                .antMatchers("/forum/find/").authenticated()
                .antMatchers("/forum/send/").authenticated()
                .and()
                // -- common configuration
                .formLogin()
                .loginPage("/show/login-page")
                .usernameParameter("login")
                .passwordParameter("password")
                .defaultSuccessUrl("/show/profile-page")
                .failureUrl("/show/error-page")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/show/logout-page"))
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .and()
                .rememberMe()
                .rememberMeParameter("remember")
                .tokenRepository(tokenRepository());
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
}
