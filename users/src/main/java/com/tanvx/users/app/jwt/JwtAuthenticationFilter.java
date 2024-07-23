package com.tanvx.users.app.jwt;

import com.tanvx.users.app.jwt.JwtService;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final HandlerExceptionResolver handlerExceptionResolver;

  private final JwtService jwtService;

  private final UserDetailsService userDetailsService;

  @Override
  protected void doFilterInternal(
      @Nonnull HttpServletRequest request,
      @Nonnull HttpServletResponse response,
      @Nonnull FilterChain filterChain) throws ServletException, IOException {
    log.info("HttpServletRequest={}", request);
    final String authHeader = request.getHeader("Authorization");
    if (Objects.isNull(authHeader) || !authHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    final String jwt;
    final String email;
    try {
      jwt = authHeader.substring(7);
      email = jwtService.extractUsername(jwt);
      if (Objects.nonNull(email) && Objects.isNull(
          SecurityContextHolder.getContext().getAuthentication())) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);
        if (jwtService.isValidToken(jwt, userDetails)) {
          UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
              userDetails, null, userDetails.getAuthorities());
          authenticationToken.setDetails(
              new WebAuthenticationDetailsSource().buildDetails(request)
          );
          SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
      }
      filterChain.doFilter(request, response);
    } catch (Exception e) {
      handlerExceptionResolver.resolveException(request, response, null, e);
    }
  }
}
