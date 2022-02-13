package com.controller;

import java.util.Date;
import java.util.stream.Collectors;
import com.util.Constants;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
public class AuthController {
    AuthenticationManager authManager;

    public AuthController(AuthenticationManager authManager) {
        this.authManager = authManager;

    }

    @PostMapping(value = "login")
    public String login(@RequestParam("user") String user, @RequestParam("pwd") String pwd) {
        Authentication autentication = authManager
                .authenticate(new UsernamePasswordAuthenticationToken(user, pwd));
        // Si el usuario esta autenticado, se genera el token
        if (autentication.isAuthenticated()) {
            return getToken(autentication);
        } else {
            return "No autenticado";
        }

    }

    private String getToken(Authentication autentication) {
        // En el body del token se incluye el usuario y los roles a los que pertenence
        // Ademas de la fecha de caducidad y los datos de la firma
        String token = Jwts
                .builder()
                .setIssuedAt(new Date()) // Fecha de creacion
                .setSubject(autentication.getName()) // usuario
                .claim("authorities", autentication // roles
                        .getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .setExpiration(new Date(System.currentTimeMillis() + Constants.getTimelive())) // fecha caducidad
                .signWith(SignatureAlgorithm.HS512, Constants.getKey()) //clave y algoritmo para la firma
                .compact(); // generacion del token

        return token;
    }

    
}
