package vin.pth.security.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import vin.pth.security.service.SecurityUserService;

@Slf4j
public class SecurityUserServiceDefaultImpl implements SecurityUserService {

  @Override
  public UserDetails getByPhone(String phone) {
    return buildUserDetails();
  }

  @Override
  public UserDetails getCurrentUserUserDetails() {
    return buildUserDetails();
  }

  @Override
  public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
    return buildUserDetails();
  }


  private UserDetails buildUserDetails(){
    log.error("默认返回一个空UserDetails，请自行实现SecurityUserService");
    return new UserDetails() {
      @Override
      public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
      }

      @Override
      public String getPassword() {
        return "";
      }

      @Override
      public String getUsername() {
        return "";
      }

      @Override
      public boolean isAccountNonExpired() {
        return true;
      }

      @Override
      public boolean isAccountNonLocked() {
        return true;
      }

      @Override
      public boolean isCredentialsNonExpired() {
        return true;
      }

      @Override
      public boolean isEnabled() {
        return true;
      }
    };
  }
}
