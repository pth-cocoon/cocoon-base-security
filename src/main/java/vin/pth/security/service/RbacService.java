package vin.pth.security.service;

import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

/**
 * RBAC权限控制服务
 */
public interface RbacService {

  /**
   * 判断用户是否有权限访问服务
   * @param request 请求，通常可以通过request获取URL，Method等信息
   * @param authentication SpringSecurity的认证对象,内部封装了由provider传入的认证对象
   * @return 有权限返回true，否则返回false
   */
  boolean hasPermission(HttpServletRequest request, Authentication authentication);

}
