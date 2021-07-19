package vin.pth.security.factory;

import java.util.Map;
import org.springframework.security.authentication.AbstractAuthenticationToken;

public interface TokenFactory {


  AbstractAuthenticationToken getTokenByParam(Map<Object, Object> param);

}
