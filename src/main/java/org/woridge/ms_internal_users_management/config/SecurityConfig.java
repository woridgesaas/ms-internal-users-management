package org.woridge.ms_internal_users_management.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;
import org.woridge.ms_internal_users_management.filter.InternalRequestFilter;

@EnableWebSecurity
@Configuration
@AllArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {


    private final CorsConfigurationSource corsConfigurationSource;
    private final InternalRequestFilter internalRequestFilter;
    @Bean
    public SecurityFilterChain customSecurityFilterChain(HttpSecurity http) throws Exception {

        return http
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                        session
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(request ->
                        request
                                .anyRequest()
                                .permitAll()
                )
                .addFilterBefore(internalRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }



}
