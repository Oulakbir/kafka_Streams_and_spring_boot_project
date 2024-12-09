package ma.enset.kafka_streams_spring_boot_project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Bean for UserDetailsService
    @Bean
    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(
                User.withUsername("user")
                        .password(passwordEncoder().encode("password"))  // Use password encoder
                        .roles("USER")
                        .build());
    }

    // PasswordEncoder bean (NoOpPasswordEncoder for testing purposes)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Use BCrypt for production
    }

    // HttpSecurity configuration to handle security settings
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .sessionManagement(sm->sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(csrf->csrf.disable())
                .headers(h->h.frameOptions(fo->fo.disable()))
                .authorizeHttpRequests(ar->ar.requestMatchers("/**","/click-producer", "/clicks/counts","/h2-console/").permitAll())
                .authorizeHttpRequests(ar->ar.anyRequest().authenticated())
                .build();
    }
}

