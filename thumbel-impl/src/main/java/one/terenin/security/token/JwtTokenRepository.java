package one.terenin.security.token;

import com.fasterxml.jackson.databind.deser.std.ObjectArrayDeserializer;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import lombok.Getter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Repository
public class JwtTokenRepository implements CsrfTokenRepository {

    @Getter
    private final String secret;

    public JwtTokenRepository() {
        this.secret = "generated";
    }

    @Override
    public CsrfToken generateToken(HttpServletRequest request) {
        /*this.secret = request.getHeader(HttpServletRequest.FORM_AUTH);*/
        // основа для токена
        String id = UUID.randomUUID().toString().replace("-", "");
        Date now = new Date();
        // время сгорания токена
        Date exp = Date.from(LocalDateTime.now().plusMinutes(30).atZone(ZoneId.systemDefault()).toInstant());
        String token = "";
        try{
            token = Jwts.builder()
                    .setId(id)
                    .setIssuedAt(now)
                    .setNotBefore(now)
                    .setExpiration(exp)
                    .signWith(SignatureAlgorithm.HS256, secret)
                    .compact();
        }catch (JwtException e){
            e.printStackTrace();
        }
        return new DefaultCsrfToken("x-csrf-token", "_csrf", token);
    }

    @Override
    public void saveToken(CsrfToken token, HttpServletRequest request, HttpServletResponse response) {
        if (Objects.nonNull(token)){
            // для js нам нужен такой хедер, сам хедер содержит список заголовков через запятую,
            // в него пихаем имя заголовка где будет лежать токен
            if (!response.getHeaderNames().contains("ACCESS_CONTROL_EXPOSE_HEADERS")){
                response.addHeader("ACCESS_CONTROL_EXPOSE_HEADERS", token.getHeaderName());
            }
            // хедер теперь точно есть
            // смотрим, есть ли в http ответе заголовок для токена
            if (response.getHeaderNames().contains(token.getHeaderName())){
                // есть, записываем токен
                response.setHeader(token.getHeaderName(), token.getToken());
            }else {
                // нет, добавляем заголовок и кидаем туда токен
                response.addHeader(token.getHeaderName(), token.getToken());
            }
        }
    }

    // штука, для того чтобы получить токен с запроса пользователя
    @Override
    public CsrfToken loadToken(HttpServletRequest request) {
        // загрузка токена из реквеста, для последующей обработки
        return (CsrfToken) request.getAttribute(CsrfToken.class.getName());
    }

    public void clearToken(HttpServletResponse response){
        // очистка заголовка с токеном
        if (response.getHeaderNames().contains("x-csrf-token")){
            response.setHeader("x-csrf-token", "");
        }
    }
}
