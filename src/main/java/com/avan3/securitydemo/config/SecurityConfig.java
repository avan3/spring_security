package com.avan3.securitydemo.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final static List<UserDetails> APPLICATION_USERS = Arrays.asList(
            new User("admin@test.com", "password", Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN"))),
            new User("user@test.com", "password", Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")))
        );

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(new AntPathRequestMatcher("/public")).permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

  @Bean
  public UserDetailsService userDetailsService() {
    //   UserDetails user =
    //           User.withDefaultPasswordEncoder()
    //                   .username("user")
    //                   .password("password")
    //                   .build();

    //   return new InMemoryUserDetailsManager(user);
    return new UserDetailsService() {

        @Override
        public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
            return APPLICATION_USERS
                .stream()
                .filter(user -> user.getUsername().equals(email))
                .findFirst()
                .orElseThrow(() -> new UsernameNotFoundException("Not user was found!"));
        }
         
    };
  }
}
