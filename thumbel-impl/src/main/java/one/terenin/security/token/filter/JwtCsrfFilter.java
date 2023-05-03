package one.terenin.security.token.filter;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import liquibase.util.StringUtils;
import lombok.RequiredArgsConstructor;
import one.terenin.security.token.JwtTokenRepository;
import org.mapstruct.ap.shaded.freemarker.template.utility.StringUtil;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;
import org.springframework.security.web.csrf.MissingCsrfTokenException;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtCsrfFilter extends OncePerRequestFilter {

    private final CsrfTokenRepository repository;
    private final HandlerExceptionResolver resolver;

    @Override // здесь описана логика по которой валидируется или генерируется токен
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        request.setAttribute(HttpServletResponse.class.getName(), response);
        // подгружаем токен из репозитория от нашего реквеста
        CsrfToken token = repository.loadToken(request);
        // проверяем погрузился ли токен из репозитория
        boolean isNotTokenPresent = token == null;
        if (isNotTokenPresent){
            // если токена не оказалось, то сгенерируем новый и сохраним его в репозиторий
            token = repository.generateToken(request);
            repository.saveToken(token, request, response);
        }
        // токен теперь точно есть, тогда засетим его в аттрибуты и параметры http запроса таким образом
        request.setAttribute(CsrfToken.class.getName(), token);
        request.setAttribute(token.getParameterName(), token);
        // проверяем путь от которого шёл запрос
        if (request.getServletPath().equals("/auth/login/")){
            // если путь auth/login/, то тогда просто пропускаем запрос, пользователь просто хочет залогиниться,
            // а токен был выдан ему выше
            try {
                filterChain.doFilter(request, response);
            }catch (Exception e){
                resolver.resolveException(request,
                        response,
                        null,
                        new MissingCsrfTokenException("missing token:" + token));
            }
        }else {
            // а если пользователь уже залогинен, то стоит проверить его токен. Получаем токен для начала из заголовка
            String actualToken = request.getHeader(token.getHeaderName());
            // не получилось -> попробуем поискать в параметрах
            if (actualToken == null){
                actualToken = request.getParameter(token.getParameterName());
            }
            try{
                // если токен не пустой, парсим и валидируем его и пускаем фильтрацию дальше
                if (!StringUtils.isEmpty(actualToken)){
                    Jwts.parser()
                            .setSigningKey(((JwtTokenRepository) repository).getSecret())
                            .parseClaimsJwt(actualToken);
                    filterChain.doFilter(request, response);
                }else {
                    resolver.resolveException(request, response, null, new InvalidCsrfTokenException(token, actualToken));
                }
            }catch (JwtException e){
                if (this.logger.isDebugEnabled()){
                    this.logger.debug("Invalid CSRF token found for " + UrlUtils.buildFullRequestUrl(request));
                }
                if (isNotTokenPresent){
                    resolver.resolveException(request, response, null, new MissingCsrfTokenException(token.getToken()));
                }else {
                    resolver.resolveException(request, response, null, new InvalidCsrfTokenException(token, actualToken));
                }
            }
        }
    }
}
