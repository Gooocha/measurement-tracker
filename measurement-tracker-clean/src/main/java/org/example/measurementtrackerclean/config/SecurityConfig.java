package org.example.measurementtrackerclean.config;
import lombok.RequiredArgsConstructor;import org.example.measurementtrackerclean.repository.UserRepository;import org.springframework.context.annotation.*;import org.springframework.security.authentication.*;import org.springframework.security.config.annotation.web.builders.HttpSecurity;import org.springframework.security.config.http.SessionCreationPolicy;import org.springframework.security.core.userdetails.*;import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;import org.springframework.security.crypto.password.PasswordEncoder;import org.springframework.security.web.*;import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@Configuration @RequiredArgsConstructor
public class SecurityConfig {
 private final JwtAuthFilter filter; private final UserRepository users;
 @Bean UserDetailsService uds(){return u->users.findByUsername(u).map(x->User.withUsername(x.getUsername()).password(x.getPassword()).roles("USER").build()).orElseThrow();}
 @Bean PasswordEncoder pe(){return new BCryptPasswordEncoder();}
 @Bean AuthenticationManager am(AuthenticationConfiguration c) throws Exception{return c.getAuthenticationManager();}
 @Bean SecurityFilterChain sc(HttpSecurity http) throws Exception {http.csrf(c->c.disable()).sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).authorizeHttpRequests(a->a.requestMatchers("/auth/**","/swagger-ui/**","/v3/api-docs/**","/","/ui/**").permitAll().anyRequest().authenticated()).addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class); return http.build();}
}
