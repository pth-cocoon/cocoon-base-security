package vin.pth.security.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import vin.pth.security.exception.LoginException;
import vin.pth.security.service.TokenService;

/**
 * @author xce
 * @date 2019/10/23 17:45
 */
@Component
public class TokenFilter extends OncePerRequestFilter {


  private final UserDetailsService userDetailsService;
  private final TokenService tokenService;

  public TokenFilter(UserDetailsService userDetailsService, TokenService tokenService) {
    this.userDetailsService = userDetailsService;
    this.tokenService = tokenService;
  }

  protected String getJwtToken(HttpServletRequest request) {
    return request.getHeader(HttpHeaders.AUTHORIZATION);
  }

  /**
   * 根据请求头中的 Authorization 里的token校验用户.
   */
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws ServletException, IOException {
    String token = getJwtToken(request);
    if (StringUtils.hasText(token)) {
      String username = tokenService.getUsernameFromToken(token);
      if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (userDetails == null) {
          throw new LoginException(this.getClass(), "未找到用户！");
        }
        if (tokenService.validateToken(token, userDetails)) {
          UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
              null,
              userDetails.getAuthorities());
          authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          SecurityContextHolder.getContext().setAuthentication(authentication);
        }
      }
    }
    chain.doFilter(request, response);
  }
}
