package net.masarakki.simple_oauth2.models;

import static org.junit.Assert.*;

import org.junit.Test;

public class AuthorizationCodeTest {

  @Test
  public void testCode() {
    String code_str = "code";
    AuthorizationCode code = new AuthorizationCode(code_str);
    assertEquals(code_str, code.getCode());
  }
}
