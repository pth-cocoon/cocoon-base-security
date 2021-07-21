package vin.pth.security.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import vin.pth.security.factory.DefaultTokenFactory;
import vin.pth.security.factory.TokenFactory;
import vin.pth.security.service.RbacService;
import vin.pth.security.service.TokenService;
import vin.pth.security.service.impl.RbacServiceDefaultImpl;
import vin.pth.security.service.impl.TokenServiceDefaultImpl;

/**
 * bean注册中心，注册了大量对默认实现，使用了ConditionalOnMissingBean注解，支持覆盖实现
 */
@Configuration
public class SecurityBeanConfig {

  /**
   * 默认密码处理器,没有额外配置密码处理器时使用BCryptPasswordEncoder作为密码处理器.
   *
   * @return PasswordEncoder.
   */
  @Bean
  @ConditionalOnMissingBean(PasswordEncoder.class)
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  @ConditionalOnMissingBean(RbacService.class)
  public RbacService rbacService() {
    return new RbacServiceDefaultImpl();
  }

  @Bean
  @ConditionalOnMissingBean(TokenService.class)
  public TokenService jwtService() {
    return new TokenServiceDefaultImpl();
  }

//  @Bean
//  @ConditionalOnMissingBean(UserDetailsService.class)
//  public UserDetailsService securityUserService() {
//    return new SecurityUserServiceDefaultImpl();
//  }

  @Bean
  @ConditionalOnMissingBean(TokenFactory.class)
  public TokenFactory tokenFactory() {
    return new DefaultTokenFactory();
  }

  /**
   * 跨域.
   */
  @Bean
  public CorsFilter corsFilter() {
    CorsConfiguration corsConfiguration = new CorsConfiguration();
    corsConfiguration.addAllowedOrigin("*");
    corsConfiguration.addAllowedHeader("*");
    corsConfiguration.addAllowedMethod("*");
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", corsConfiguration);
    return new CorsFilter(source);
  }

}
