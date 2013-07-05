package net.masarakki.simple_oauth2.models;

public class AuthorizationCode {
  protected final String code;

  public AuthorizationCode(String code) {
    this.code = code;
  }

  public String getCode() {
    return code;
  }
}
