package ml.socshared.service.mail.security.jwt;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ml.socshared.service.mail.security.client.AuthClient;
import ml.socshared.service.mail.security.model.ServiceDetails;
import ml.socshared.service.mail.security.request.CheckTokenRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${service.id}")
    private String serviceId;

    private final AuthClient authClient;

    public UserDetails getServiceDetails(String token) {
        Claims claims =  Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        String role = claims.get("role", String.class);

        return ServiceDetails.builder()
                .authorities(Collections.singleton(new SimpleGrantedAuthority("ROLE_" + role)))
                .username(claims.get("from_service", String.class))
                .accountNonLocked(true)
                .build();
    }

    public boolean validateServiceToken(String token) {
        try {
            Jws<Claims> claims = getJwsClaimsFromToken(token);
            String toServiceId = claims.getBody().get("to_service", String.class);
            Date date = claims.getBody().getExpiration();
            if (date.before(new Date())) {
                log.warn("JWT Token is expired.");
                return false;
            }

            CheckTokenRequest request = new CheckTokenRequest();
            request.setToken(token);

            return serviceId.equals(toServiceId) && authClient.send(request).getSuccess();
        } catch (JwtException | IllegalArgumentException exc) {
            if (exc instanceof ExpiredJwtException) {
                log.warn("JWT Token is expired.");
            } else {
                log.warn("JWT Token is invalid.");
            }
            return false;
        }
    }

    private Jws<Claims> getJwsClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
    }

    public String resolveToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer")) {
            return token.substring(6).trim();
        }
        return null;
    }
}
