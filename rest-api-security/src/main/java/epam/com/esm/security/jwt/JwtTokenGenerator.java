package epam.com.esm.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * JwtTokenGenerator is the service class, provides jwt token operations
 */
@Component
public class JwtTokenGenerator {

    /**
     * Value holds jwt secret
     */
    @Value("${jwt.secret}")
    private String secret;

    /**
     * Value holds jwt lifetime
     */
    @Value(("${jwt.lifetime}"))
    private Long lifetime;

    /**
     * Generates token from user details attributes
     *
     * @param userDetails provided user details user
     * @return {@code String} generated token
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", userDetails.getUsername());
        claims.put("roles", getRoles(userDetails));

        Date issuedDate = new Date();
        Date expirationDate = new Date(issuedDate.getTime() + lifetime);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(issuedDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    /**
     * Gets username from token
     *
     * @param token provided token
     * @return {@code String} username from token
     */
    public String getUsername(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    /**
     * Gets roles from token
     *
     * @param token provided token
     * @return {@code List<String>} list of roles from token
     */
    public List<String> getRoles(String token) {
        return getClaimsFromToken(token).get("roles", List.class);
    }

    /**
     * Parses claims from token
     *
     * @param token provided token
     * @return {@code Claims} retrieved claims object
     */
    private Claims getClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    /**
     * Retrieves roles from user details user
     *
     * @param userDetails provided user details user
     * @return {@code List<String>} list of roles from user details
     */
    private List<String> getRoles(UserDetails userDetails) {
        return userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toList());
    }
}