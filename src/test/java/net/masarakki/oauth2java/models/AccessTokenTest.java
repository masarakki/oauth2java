package net.masarakki.oauth2java.models;

import static org.junit.Assert.*;

import net.arnx.jsonic.JSON;
import net.masarakki.oauth2java.models.AccessToken;
import org.json.simple.JSONObject;
import org.junit.Test;

public class AccessTokenTest {
  private static final String accessTokenString = "ACCESS_TOKEN_STRING";
  private static final String refreshTokenString = "REFRESH_TOKEN_STRING";
  private static final int expiresInteger = 7200;

  @Test
  public void testInvalidResponse() {
    AccessToken access_token = JSON.decode("{}", AccessToken.class);
    assertFalse(access_token.isValid());
  }

  @Test
  public void testParseResponseWithRefreshTokenAndExpiresIn() {
    String response = buildResponse(true, true);
    AccessToken access_token = JSON.decode(response, AccessToken.class);
    assertTrue(access_token.isValid());
    assertEquals(AccessTokenTest.accessTokenString, access_token.getAccessToken());
    assertEquals(AccessTokenTest.refreshTokenString, access_token.getRefreshToken());
    assertTrue(access_token.isRefreshable());
    assertFalse(access_token.isExpired());
  }

  @Test
  public void testParseResponseWithoutRefreshToken() {
    String response = buildResponse(false, false);
    AccessToken access_token = JSON.decode(response, AccessToken.class);
    assertTrue(access_token.isValid());
    assertEquals(AccessTokenTest.accessTokenString, access_token.getAccessToken());
    assertNull(access_token.getRefreshToken());
    assertFalse(access_token.isRefreshable());
    assertFalse(access_token.isExpired());
  }

  @SuppressWarnings("unchecked")
  public String buildResponse(boolean has_refresh_token, boolean has_expires_in) {
    JSONObject json = new JSONObject();
    json.put("access_token", AccessTokenTest.accessTokenString);
    if (has_refresh_token) {
      json.put("refresh_token", AccessTokenTest.refreshTokenString);
    }
    if (has_expires_in) {
      json.put("expires_in", AccessTokenTest.expiresInteger);
    }
    return json.toJSONString();
  }

}
