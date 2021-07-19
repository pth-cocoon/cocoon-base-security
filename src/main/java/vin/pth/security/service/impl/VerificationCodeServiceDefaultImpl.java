package vin.pth.security.service.impl;

import lombok.extern.slf4j.Slf4j;
import vin.pth.security.service.VerificationCodeService;

@Slf4j
public class VerificationCodeServiceDefaultImpl implements VerificationCodeService {

  @Override
  public void sendVerificationCode(String address, String code) {
    log.info("您的手机号是{}，您的验证码是{}", address, code);
    log.info("如需发送验证码，请在本接口实现验证码逻辑");
  }

  @Override
  public boolean verify(String address, String code) {
    log.error("永远校验通过，请自行实现VerificationCodeService");
    return true;
  }
}
