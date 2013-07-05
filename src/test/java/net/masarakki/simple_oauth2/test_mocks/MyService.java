package net.masarakki.simple_oauth2.test_mocks;

import net.masarakki.simple_oauth2.models.OAuthService;

public class MyService extends OAuthService {
  private final String base_url = "http://localhost:3000";

  @Override
  public String getAuthrozationUrl() {
    return base_url + "/oauth/authorize";
  }

  @Override
  public String getAccessTokenUrl() {
    return base_url + "/oauth/token";
  }

}
