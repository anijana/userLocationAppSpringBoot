package com.example.UserLocationApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class AdminRenderConfig {
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("admin")
                .roles("ADMIN")
                .build();

        UserDetails reader = User.withDefaultPasswordEncoder()
                .username("reader")
                .password("reader")
                .roles("READER")
                .build();

        return new InMemoryUserDetailsManager(admin, reader);
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests((authz) -> authz
                        .requestMatchers(HttpMethod.GET, "/api/user_location/**").hasAnyRole("ADMIN", "READER")
                        .requestMatchers(HttpMethod.POST, "/api/user_location/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/user_location/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/user_location/**").hasRole("ADMIN")
                )
                .httpBasic(withDefaults());
        return http.build();
    }
}
