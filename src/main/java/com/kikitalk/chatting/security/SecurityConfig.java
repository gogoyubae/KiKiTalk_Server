package com.kikitalk.chatting.security;

import com.kikitalk.chatting.CorsConfig;
import com.kikitalk.chatting.security.jwt.JwtFilter;
import com.kikitalk.chatting.security.oauth2.OAuth2AuthenticationSuccessHandler;
import com.kikitalk.chatting.security.oauth2.OAuth2Service;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final OAuth2Service OAuth2Service;
    private final OAuth2AuthenticationSuccessHandler successHandler;
    private final JwtFilter jwtFilter;
    private final CorsConfig corsConfig;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        .cors(cors -> cors.configurationSource(corsConfig.corsConfigurationSource()))

                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/oauth2/**","/home").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(OAuth2Service) // OAuth2UserService 등록
                        )
                        .successHandler(successHandler)
                        .failureUrl("/login?error=true")
                );
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
