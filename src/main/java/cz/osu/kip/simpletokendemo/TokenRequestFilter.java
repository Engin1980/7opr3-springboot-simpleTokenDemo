package cz.osu.kip.simpletokendemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class TokenRequestFilter extends OncePerRequestFilter {

  @Autowired private TokenManager tokenManager;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
          throws ServletException, IOException {
    final String token = request.getHeader("Authorization");

    if (token != null) {
      Optional<AppUser> userOpt = tokenManager.validateAndGetUser(token);
      if (userOpt.isEmpty()) {
        logger.info("Found some token, but seems not to be valid.");
      } else {
        AppUser user = userOpt.get();
        List<GrantedAuthority> roles = new ArrayList<>();
        UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(
                user, null, roles);
        upat.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(upat);
      }
    } else {
      logger.info("No auth token found");
    }

    chain.doFilter(request, response);
  }
}
