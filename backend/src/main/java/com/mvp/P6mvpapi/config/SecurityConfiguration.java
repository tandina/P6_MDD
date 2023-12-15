package com.mvp.P6mvpapi.config;

import com.mvp.P6mvpapi.models.Role;
import com.mvp.P6mvpapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springframework.security.authorization.AuthorityReactiveAuthorizationManager.hasAnyAuthority;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserService userService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf().disable()
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/api/auth/signing").permitAll()
                        .requestMatchers("/api/auth/signup").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/v3/**").permitAll()
                        .requestMatchers("/api-docs").permitAll()
                        .requestMatchers("/api/auth/themes").hasAnyAuthority(Role.USER.name())
                        .requestMatchers("/api/auth/article").hasAnyAuthority(Role.USER.name())
                        .requestMatchers("/api/user/auth/{id}/update").hasAnyAuthority(Role.USER.name())
                        .requestMatchers("/api/user/auth").hasAnyAuthority(Role.USER.name())

                        .requestMatchers("/api/user/auth/{userId}/articles").hasAnyAuthority(Role.USER.name())
                        .requestMatchers("/api/user/auth/{userId}/themes").hasAnyAuthority(Role.USER.name())
                        .requestMatchers("/api/auth/article/create").hasAnyAuthority(Role.USER.name())
                        .requestMatchers("auth/{userId}/themes/{themeId}/subscribe").hasAnyAuthority(Role.USER.name())
                        .requestMatchers("auth/{userId}/themes/{themeId}/unsubscribe").hasAnyAuthority(Role.USER.name())
                        .requestMatchers("auth/{articleId}/comments").hasAnyAuthority(Role.USER.name())

                        .requestMatchers("/api/auth/user").hasAnyAuthority(Role.USER.name())

                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService.userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }
   /*  @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:4200")
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedHeaders(HttpHeaders.CONTENT_TYPE,
                                HttpHeaders.AUTHORIZATION)
                                .exposedHeaders(HttpHeaders.AUTHORIZATION);
            }
        };
    }*/

}
