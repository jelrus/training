package epam.com.esm.security.filter;

import epam.com.esm.security.jwt.JwtTokenGenerator;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

/**
 * JwtRequestFilter is the service class, provides methods for token validation
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    /**
     * Holds JwtTokenGenerator object
     */
    private final JwtTokenGenerator jwtTokenGenerator;

    /**
     * Holds HandlerExceptionResolver object
     */
    private final HandlerExceptionResolver resolver;

    /**
     * Constructs JwtRequestFilter object with provided JwtTokenGenerator and HandlerExceptionResolver objects
     *
     * @param jwtTokenGenerator service, provides operations on jwt tokens
     * @param resolver          interface, provides contracts for exception resolving
     */
    public JwtRequestFilter(JwtTokenGenerator jwtTokenGenerator,
                            @Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.jwtTokenGenerator = jwtTokenGenerator;
        this.resolver = resolver;
    }

    /**
     * Validates and puts token to security context holder
     *
     * @param request           provided http servlet request
     * @param response          provided http servlet response
     * @param filterChain       provided filter chain
     * @throws ServletException if servlet configuration fails
     * @throws IOException      if input corrupted
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String username = null;
        String token = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);

            try {
                username = jwtTokenGenerator.getUsername(token);
            } catch (SignatureException | ExpiredJwtException e) {
                resolver.resolveException(request, response, null, e);
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    jwtTokenGenerator.getRoles(token)
                            .stream()
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList())
            );
            SecurityContextHolder.getContext().setAuthentication(upToken);
        }

        filterChain.doFilter(request, response);
    }
}