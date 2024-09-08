package application.config;


import application.service.UserDetailsServiceImp;
import application.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Security configuration to define the permissions of each user level.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private final UserDetailsServiceImp userDetailsServiceImp;

  private final JwtAuthenticationFilter jwtAuthenticationFilter;

  private final CustomLogoutHandler logoutHandler;

  /**
   * Creates a new SecurityConfig instance.
   *
   * @param userDetailsServiceImp The UserDetailsService implementation for retrieving user details.
   * @param jwtAuthenticationFilter The JWT authentication filter.
   * @param logoutHandler The custom logout handler.
   */
  public SecurityConfig(UserDetailsServiceImp userDetailsServiceImp,
      JwtAuthenticationFilter jwtAuthenticationFilter, CustomLogoutHandler logoutHandler) {
    this.userDetailsServiceImp = userDetailsServiceImp;
    this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    this.logoutHandler = logoutHandler;
  }

  /**
   * Defines the authority of each user level.
   *
   * @param http The HttpSecurity object to configure security settings.
   * @return The SecurityFilterChain object representing the security filter chain.
   * @throws Exception if an error occurs during configuration.
   */
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    return http.csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(req -> req.requestMatchers("/login/**", "/register/**").permitAll()
            .requestMatchers("/asset/find/**", "/type/find/**", "/comment/**", "/log/**",
                "user/{username}")
            .hasAnyAuthority("USER", "ADMIN", "READER")
            .requestMatchers("/type/find/**", "/asset/**", "/comment/**", "log/**")
            .hasAnyAuthority("USER", "ADMIN").requestMatchers("/type/**", "user/**")
            .hasAuthority("ADMIN").anyRequest().authenticated())
        .userDetailsService(userDetailsServiceImp)
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .exceptionHandling(e -> e
            .accessDeniedHandler(
                (request, response, accessDeniedException) -> response.setStatus(403))
            .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
        .logout(l -> l.logoutUrl("/logout").addLogoutHandler(logoutHandler).logoutSuccessHandler(
            (request, response, authentication) -> SecurityContextHolder.clearContext()))
        .build();

  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
      throws Exception {
    return configuration.getAuthenticationManager();
  }


}
