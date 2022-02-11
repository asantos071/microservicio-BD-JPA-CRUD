package com.app;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.util.Constants;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JWTauthorizationFilter extends BasicAuthenticationFilter {

    public JWTauthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String header = request.getHeader(Constants.getHeader());
        if (header == null || !header.startsWith(Constants.getPrefixtoken())) {
            chain.doFilter(request, response);
            return;
        }
        // Si pasa el if, el token tiene los datos de la autenticacion
        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
        // La informacion del usuario se almacena en el contexto de seguridad
        // para que pueda ser utilizada por Spring Security durane el proceso
        // de autorizacion
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(Constants.getHeader());
        if (token != null) {
            // Se procesa el token y se recupera el usuario y los roles
            Claims claims = Jwts.parser()
                    .setSigningKey(Constants.getKey())
                    .parseClaimsJws(token.replace(Constants.getPrefixtoken(), ""))
                    .getBody();

            String user = claims.getSubject(); // usuario
            List<String> authorities = (List<String>) claims.get("authorities"); // roles
            if (user != null) {
                // creamos el objeto con la información del usuario
                return (new UsernamePasswordAuthenticationToken(user,
                        authorities
                                .stream()
                                .map(SimpleGrantedAuthority::new)
                                .collect(Collectors.toList())));
            }

        }
        return null;
    }

}
