package com.app;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // Definicion de roles e usuarios
        auth
                .inMemoryAuthentication()
                .withUser("user1")
                .password("{noop}user1") // lo de {noop} se pone para no obligar a usar mecanismo de encriptación
                .roles("USER")
                .and()
                .withUser("admin")
                .password("{noop}admin")
                .roles("ADMIN");

        // Lo siguiente es para encriptar la Password
        /*
         * auth
         * .inMemoryAuthentication()
         * .withUser("user1")
         * .password(new BCryptPasswordEncoder().encode("user1")) // Se usa mecanismo de
         * encriptacion
         * .roles("USER")
         * .and()
         * .withUser("admin")
         * .password(new BCryptPasswordEncoder().encode("admin"))
         * .roles("USER", "ADMIN");
         */

        // La siguiente configuracion es para usuarios de BD
        /*
         * DataSource dataSource = null;
         * auth
         * .jdbcAuthentication().dataSource(dataSource).
         * usersByUsernameQuery("select username, password, enable"
         * + " from users where username=?")
         * .authoritiesByUsernameQuery("select username, authority " +
         * "from authorities where username=?");
         */
    }

    @Override
    // Definicion de politicas de seguridad
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeRequests()
                // solo los miembros del rol admin podrán realizar altas
                // y para ver la lista de contactos, tendrán que estar autenticados
                .antMatchers(HttpMethod.POST, "/contactos").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/contactos/").hasRole("ADMIN")
                .antMatchers("/contactos").authenticated()
                // .antMatchers("/**").authenticated()
                // .antMatchers("/contactos/**").authenticated()
                .and()
                .httpBasic();
    }

}
