package vin.pth.security.service.impl;

import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import vin.pth.security.service.RbacService;

@Slf4j
public class RbacServiceDefaultImpl implements RbacService {

  @Override
  public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
    log.error("默认RbacService实现拥有全部权限，请自行实现 RbacService");
    return true;
  }
}
