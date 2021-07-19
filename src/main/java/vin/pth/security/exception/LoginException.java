package vin.pth.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author xce
 * @date 2020/1/9  9:43
 */
public class LoginException extends AuthenticationException  {

  private int code;
  private final Class<?> clazz;

  public LoginException(Class<?> clazz, String msg) {
    super(msg);
    this.clazz = clazz;
  }

  public int getCode() {
    return this.code;
  }

  public Class<?> getClazz() {
    return this.clazz;
  }
}
