package one.terenin.security.token.filter.actual;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import one.terenin.entity.UserEntity;
import one.terenin.security.details.UserDetailsServiceBase;
import one.terenin.security.token.filter.common.SecurityConstants;
import one.terenin.security.token.filter.common.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Slf4j
public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils utils;
    @Autowired
    private UserDetailsServiceBase userDetailsService;

    /**
     *
     * @apiNote
     * Why not a required arg constructor?
     * I got one reason for it, and that's because this class should be with closed api
     * just because it is a filter
     *
     * */

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try{
            String token = parseJwt(request);
            if (Objects.nonNull(token) && utils.checkToken(token)){
                log.info("{}{}", "get valid token: ", token);
                String login = utils.getLoginFromToken(token);
                log.info("{}{}", "get login from token", token);
                UserDetails details = userDetailsService.loadUserByUsername(login);
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(details, null, details.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                log.info("token was injected to security context");
            }
        }catch (Exception e){
            log.error("troubles on token parsing");
            log.trace(e.getMessage());
        }
        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request){
        String token = request.getHeader(SecurityConstants.HEADER_NAME);
        if (Objects.nonNull(token)
                && StringUtils.hasText(token)
                && token.startsWith(SecurityConstants.TOKEN_PREFIX)){
            return token.substring(SecurityConstants.TOKEN_PREFIX.length(), token.length());
        }
        return null;
    }

}
