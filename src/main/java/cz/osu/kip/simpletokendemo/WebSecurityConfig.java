package cz.osu.kip.simpletokendemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

  @Autowired TokenRequestFilter tokenRequestFilter;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws  Exception{
    http.csrf().disable()
            .authorizeRequests()
            .antMatchers("/user/login", "/user/logout").permitAll()
            .anyRequest().authenticated()
            .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    http.addFilterBefore(tokenRequestFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
}
