package vin.pth.security.provider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import vin.pth.security.exception.LoginException;

/**
 * @author xce
 * @date 2020/1/8  10:39
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class PasswordAuthenticationProvider implements AuthenticationProvider {

  private final UserDetailsService userDetailsService;

  private final PasswordEncoder passwordEncoder;


  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String username = (authentication.getPrincipal() == null) ? "" : authentication.getName();
    if (!StringUtils.hasText(username)) {
      log.warn("用户名不允许为空!");
      throw new LoginException(PasswordAuthenticationProvider.class, "用户名不允许为空！");
    }
    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
    if (userDetails == null) {
      log.warn("未找到该用户{}!", username);
      throw new LoginException(PasswordAuthenticationProvider.class, "未找到该用户！");
    }
    if (!passwordEncoder.matches(authentication.getCredentials().toString(), userDetails.getPassword())) {
      log.warn("密码错误!");
      throw new LoginException(PasswordAuthenticationProvider.class, "密码错误，请重新输入！");
    }
    return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(),
      userDetails.getAuthorities());
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
  }
}
