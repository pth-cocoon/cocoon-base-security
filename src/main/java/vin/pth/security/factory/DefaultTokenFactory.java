package vin.pth.security.factory;

import java.util.Map;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import vin.pth.security.exception.LoginException;

/**
 * @author xce
 * @date 2020/3/23  11:17
 */
public class DefaultTokenFactory implements TokenFactory {

  /**
   * 根据接收到的参数，返回一个合适的认证token，目前支持，用户名密码，手机号验证码
   *
   * @param params 认证参数
   * @return token
   */
  public AbstractAuthenticationToken getTokenByParam(Map<Object, Object> params) {
    try {
      params.forEach((k, v) -> v = v.toString().trim());
      if (params.get("username") != null && params.get("password") != null) {
        return new UsernamePasswordAuthenticationToken(params.get("username").toString(),
          params.get("password").toString());
      }
    } catch (Exception e) {
      throw new LoginException(DefaultTokenFactory.class, "登陆参数不完整！");
    }
    throw new LoginException(DefaultTokenFactory.class, "登陆参数不完整！");
  }

}