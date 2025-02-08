package com.seguridad.seguridad;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SeguridadConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable) // Deshabilita CSRF para pruebas (opcional)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/publico").permitAll()  // Acceso libre a /publico
                        .anyRequest().authenticated()  // Requiere autenticación para lo demás
                )
                .formLogin(form -> form
                        .defaultSuccessUrl("/privado", true)  // Redirige a /privado tras iniciar sesión
                        .permitAll()
                )
                .httpBasic(basic -> basic
                        .realmName("Spring Boot Seguridad") // Nombre del dominio para autenticación básica
                )
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails usuario = User.withUsername("usuario")
                .password("{noop}contraseña") // `{noop}` deshabilita la encriptación para pruebas
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(usuario);
    }
}
