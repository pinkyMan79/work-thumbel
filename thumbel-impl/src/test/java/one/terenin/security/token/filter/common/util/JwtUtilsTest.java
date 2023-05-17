package one.terenin.security.token.filter.common.util;

import io.jsonwebtoken.MalformedJwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class JwtUtilsTest{

    @Mock
    private Authentication authentication;

    @InjectMocks
    private JwtUtils jwtUtils;

    @Mock
    private UserDetails userDetailsImplTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(authentication.getDetails()).thenReturn(userDetailsImplTest);
        when(authentication.getPrincipal()).thenReturn(userDetailsImplTest);
        when(userDetailsImplTest.getUsername()).thenReturn("test_user");
        when(userDetailsImplTest.getPassword()).thenReturn("test_pass");
        jwtUtils.setSecret("qerg135geqrvq4tgh31tg15gh3v51credg4tqerg" +
                "135geqrvq4tgh31tg15gh3v51credg4tqerg135geqrvq4tgh31tg15gh3v51credg4t");
    }

    @Test
    void testGenerateJwToken() {
        when(authentication.getPrincipal()).thenReturn(new UserDetailsImpl("test_user", "test_password"));
        String token = jwtUtils.generateJwToken(authentication);
        assertNotNull(token);
        assertTrue(token.length() > 0);
    }

    @Test
    void testCheckToken() {
        String validToken = jwtUtils.generateJwToken(authentication);
        assertTrue(jwtUtils.checkToken(validToken));
        assertFalse(jwtUtils.checkToken("invalid_token"));
    }

    @Test
    void testGetLoginFromToken() {
        String validToken = jwtUtils.generateJwToken(authentication);
        assertEquals("test_user", jwtUtils.getLoginFromToken(validToken));
        assertThrows(MalformedJwtException.class, () -> jwtUtils.getLoginFromToken("invalid_token"));
    }

    static class UserDetailsImpl implements UserDetails {

        private final String username;
        private final String password;

        public UserDetailsImpl(String username, String password) {
            this.username = username;
            this.password = password;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return null;
        }

        @Override
        public String getPassword() {
            return password;
        }

        @Override
        public String getUsername() {
            return username;
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }


}

