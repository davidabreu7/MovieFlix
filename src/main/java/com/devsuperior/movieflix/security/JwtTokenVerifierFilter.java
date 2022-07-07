package com.devsuperior.movieflix.security;


import com.devsuperior.movieflix.config.AuthConfig;
import com.devsuperior.movieflix.config.JwtConfig;
import com.devsuperior.movieflix.controller.exception.AuthorizationError;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class JwtTokenVerifierFilter extends OncePerRequestFilter {

    private final JwtConfig jwtConfig;

    private final AuthConfig authConfig;

    public JwtTokenVerifierFilter(JwtConfig jwtConfig, AuthConfig authConfig) {
        this.jwtConfig = jwtConfig;
        this.authConfig = authConfig;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            if (!request.getMethod().equals("GET")) {
                response.setStatus(401);
                return;
            }
            filterChain.doFilter(request, response);
            return;
        }
        try {
            String token = authorizationHeader.replace("Bearer ", "");

            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(jwtConfig.getSecretKeyForSigning())
                    .build()
                    .parseClaimsJws(token);

            Claims body = claimsJws.getBody();

            String username = body.getSubject();
            authConfig.setName(username);


            var authorities = (List<Map<String, String>>) body.get("authorities");
            Set<SimpleGrantedAuthority> authority = authorities.stream()
                    .map(auth -> new SimpleGrantedAuthority(auth.get("authority")))
                    .collect(Collectors.toSet());

            Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authority);

            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);
        }
        catch (AuthenticationException | ServletException | IOException | JwtException e){
            ObjectMapper objectMapper = new ObjectMapper();
            AuthorizationError errorResponse = new AuthorizationError( "Unauthorized", e.getMessage());
            response.setContentType("application/json");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
        }
    }
}
