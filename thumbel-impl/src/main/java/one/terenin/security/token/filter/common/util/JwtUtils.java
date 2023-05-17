package one.terenin.security.token.filter.common.util;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
@Data
@PropertySource("classpath:/app.yaml")
@Slf4j
public class JwtUtils {

    @Value("${spring.security.jwt.secret}")
    private String secret;

    public String generateJwToken(Authentication authentication){
        Date now = new Date();
        Date exp = Date.from(LocalDateTime.now().plusMinutes(30).atZone(ZoneId.systemDefault()).toInstant());
        log.info("{} {} {}","making token with expiration ", now.toString() ,exp.toString());
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = Jwts.builder()
                .setSubject(userDetails.username())
                .setExpiration(exp).signWith(SignatureAlgorithm.HS256,
                        secret).compact();
        log.info("{}{}", "token generated: ", token);
        return token;

    }

    public boolean checkToken(String token){
        try{
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException me){
            log.error("{}{}", "error validation on token malformed: ", "__" + token);
            log.trace(me.getMessage());
        } catch (JwtException e){
            log.error("{}{}", "error validation on token: ", "__" + token);
            log.trace(e.getMessage());
        }
        return false;
    }

    public String getLoginFromToken(String token){
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

}
