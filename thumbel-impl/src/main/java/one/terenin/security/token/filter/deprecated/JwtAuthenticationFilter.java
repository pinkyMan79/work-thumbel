package one.terenin.security.token.filter.deprecated;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import one.terenin.dto.user.UserLoginRequest;
import one.terenin.security.token.filter.common.SecurityConstants;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

// здесь идёт процесс подтверждения личности пользователя
// стоит ли здесь перейти на AbstractAuthenticationProcessingFilter?
@Deprecated
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager manager;

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // в запросе ожидаем сущность логина, забираем её
        UserLoginRequest loginRequest = new ObjectMapper().readValue(request.getInputStream(), UserLoginRequest.class);
        // возвращаем объект аутентификации
        return manager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getLogin(),
                loginRequest.getPassword(), new ArrayList<>()));
        // что вообще происходит под капотом, сам manager вызывает наш UserDetailService от метода loadUserByUsername,
        // и смотрит есть наш пользователь в бд если такой пользователь есть, он вернёт нам уже залогиненного пользака
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        Date exp = Date.from(LocalDateTime.now().plusMinutes(30).atZone(ZoneId.systemDefault()).toInstant());
        // здесь User это стандартизированное и абстрактное представление пользователя в инфраструктуре спрнга
        String token = JWT.create()
                .withSubject(((User)authResult.getPrincipal()).username())
                .withExpiresAt(exp)
                .sign(Algorithm.HMAC256("secret"));
        response.addHeader(SecurityConstants.HEADER_NAME, SecurityConstants.TOKEN_PREFIX + token);
    }

}
