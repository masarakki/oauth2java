package net.masarakki.simple_oauth2.models;

import net.masarakki.simple_oauth2.request.Request;

public class AccessTokenRequest extends Request {

  public AccessTokenRequest(Verb verb, String url) {
    super(verb, url, AccessToken.class);
  }

  public AccessTokenRequest(Verb verb, String url, AuthorizationCode code) {
    this(verb, url);
    addCode(code);
  }

  public AccessTokenRequest(Verb verb, String url, AccessToken token) {
    this(verb, url);
    addCode(token);
  }

  public AccessTokenRequest addCode(AccessToken token) {
    this.addParam("grant_type", "refresh_token");
    this.addParam("refresh_token", token.getRefreshToken());
    return this;
  }

  public AccessTokenRequest addCode(AuthorizationCode code) {
    this.addParam("grant_type", "authorization_code");
    this.addParam("code", code.getCode());
    return this;
  }

  public boolean isValid() {
    return this.hasParam("grant_type") && !(this.hasParam("code") && this.hasParam("refresh_token"));
  }
}
