package org.personal.Vacancy_Manager.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    SecurityFilter securityFilter;

    @Autowired
    SecurityCandidateFilter securityCandidateFilter;

    private static final String[] SWAGGER_LIST = {
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-resources/**"
    };

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(auth -> {
                        auth.requestMatchers("/candidates").permitAll()
                            .requestMatchers("/companies").permitAll()
                            .requestMatchers("/companies/auth").permitAll()
                            .requestMatchers("/candidates/auth").permitAll()
                            .requestMatchers(SWAGGER_LIST).permitAll();

                        auth.anyRequest().authenticated();
                    }).addFilterBefore(securityCandidateFilter, BasicAuthenticationFilter.class)
                    .addFilterBefore(securityFilter, BasicAuthenticationFilter.class);

        return httpSecurity.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
