package com.vung.restful.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class SercurityConfig {
    
    @Bean
    public PasswordEncoder PasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http , CustomAuthenticationEntryPoint customAuthenticationEntryPoint) throws Exception {
        
        http
            .csrf(c -> c.disable())
            .cors(Customizer.withDefaults())
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/api/v1/login","/api/v1/create" , "/api/v1/").permitAll()          
                .anyRequest().authenticated())
                //.anyRequest().permitAll())
            .formLogin(f -> f.disable())
            .oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults())
            .authenticationEntryPoint(customAuthenticationEntryPoint))

            // .exceptionHandling(exceptions -> exceptions
            //                     .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint()) //401
            //                     .accessDeniedHandler(new BearerTokenAccessDeniedHandler())) // 403
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }

}
