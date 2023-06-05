package one.terenin.security.token.filter.deprecated;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwt;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import one.terenin.dto.user.UserLoginRequest;
import one.terenin.dto.user.UserRequest;
import one.terenin.entity.UserEntity;
import one.terenin.security.token.filter.common.SecurityConstants;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;


@Deprecated
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String headerAuthorization = request.getHeader(SecurityConstants.HEADER_NAME);
        // проверяем токен пользователя
        if (Objects.isNull(headerAuthorization) || !headerAuthorization.startsWith(SecurityConstants.TOKEN_PREFIX)){
            chain.doFilter(request, response);
            return;
        }
        // токен реализует Authentication потому из него можно вытащить всё что нам надо и получается
        // что мы знаем пользователя ещё до того как его реквест попадёт в обработчик(controller)
        // тем самым реализуем дополнительный стой защиты и сессионность
        UsernamePasswordAuthenticationToken token = getToken(request);
        SecurityContextHolder.getContext().setAuthentication(token);
    }

    // верификация токена
    private UsernamePasswordAuthenticationToken getToken(HttpServletRequest request){
        String token = request.getHeader(SecurityConstants.HEADER_NAME);
        if (token != null){
            // помним, что в subj лежит username, для дальнейшего вытягивания пользователя из UserDetailService
            String user = JWT.require(Algorithm.HMAC256("secret"))
                    .build()
                    .verify(token.replace(SecurityConstants.TOKEN_PREFIX, ""))
                    .getSubject();
            if (user !=null){
                return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
            }
            return null;
        }
        return null;
    }


}
