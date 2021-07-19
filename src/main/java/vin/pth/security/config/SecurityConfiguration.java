package vin.pth.security.config;

import java.util.Arrays;
import java.util.List;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import vin.pth.security.factory.TokenFactory;
import vin.pth.security.filter.LoginFilter;
import vin.pth.security.filter.TokenFilter;

/**
 * 主配置文件，对默认对SpringSecurity进行了高度对封装和默认设置，
 * @author xce
 * @date 2020/1/6  16:54
 */
@Slf4j
public abstract class SecurityConfiguration extends WebSecurityConfigurerAdapter {


  /**
   * Token拦截器，用于从request中取出Token，并将当前用户置入SecurityContextHolder
   */
  @Resource
  protected TokenFilter tokenFilter;

  /**
   * Token工厂，根据返回对Token类型，对应后续对处理。默认实现用户名密码，可自行实现短信邮箱登陆
   */
  @Resource
  protected TokenFactory tokenFactory;

  /**
   * 与TokenFactory对应对provider
   */
  @Autowired
  private List<AuthenticationProvider> authenticationProviderList;

  /**
   * 登陆成功处理
   */
  protected abstract AuthenticationSuccessHandler getLoginSuccessHandler();

  /**
   * 登陆失败处理
   */
  protected abstract AuthenticationFailureHandler getLoginFailureHandler();

  /**
   * 白名单路径
   */
  protected abstract String[] getWhiteList();

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    log.info("白名单列单：{}", Arrays.toString(getWhiteList()));

    HttpSecurity b = http.authorizeRequests()
      .antMatchers(getWhiteList()).permitAll()
      .antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources", "/configuration/security",
        "/swagger-ui.html", "/webjars/**", "/swagger-resources/configuration/ui", "/swagge‌r-ui.html").permitAll()
      .antMatchers(HttpMethod.OPTIONS).permitAll()
      .anyRequest().access("@rbacService.hasPermission(request,authentication)")
      .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      .and().cors().and().csrf().disable();
    addFilter(b);
  }

  protected void addFilter(HttpSecurity b) {
    AbstractAuthenticationProcessingFilter loginFilter = new LoginFilter(tokenFactory);
    loginFilter.setAuthenticationManager(new ProviderManager(authenticationProviderList));
    loginFilter.setAuthenticationSuccessHandler(getLoginSuccessHandler());
    loginFilter.setAuthenticationFailureHandler(getLoginFailureHandler());
    b.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class)
      .addFilterAfter(loginFilter, TokenFilter.class);

  }

}
