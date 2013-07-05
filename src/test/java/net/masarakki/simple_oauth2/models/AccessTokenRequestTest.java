package net.masarakki.simple_oauth2.models;

import static org.junit.Assert.*;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.junit.Test;

public class AccessTokenRequestTest {
  private AccessTokenRequest request;

  @Test
  public void testCheckRequiredParams() throws ClientProtocolException, IOException {
    request = new AccessTokenRequest(Verb.POST, "http://localhost:3000/oauth/token");
    assertFalse(request.isValid());
  }

  @Test
  public void testAssignAuthorizationCode() {
    request = new AccessTokenRequest(Verb.POST, "http://localhost:3000/oauth/token", new AuthorizationCode("hello"));
    assertTrue(request.isValid());
    assertTrue(request.hasParam("grant_type"));
    assertEquals("authorization_code", request.getParam("grant_type"));
    assertTrue(request.hasParam("code"));
    assertEquals("hello", request.getParam("code"));
    assertFalse(request.hasParam("refresh_token"));
  }

  @Test
  public void testAssignAccessToken() {
    request = new AccessTokenRequest(Verb.POST, "http://localhost:3000/oauth/token", new AccessToken("hello", "world", 7200));
    assertTrue(request.isValid());
    assertTrue(request.hasParam("grant_type"));
    assertEquals("refresh_token", request.getParam("grant_type"));
    assertTrue(request.hasParam("refresh_token"));
    assertEquals("world", request.getParam("refresh_token"));
    assertFalse(request.hasParam("code"));
  }

  @Test
  public void testAssignDoubleToken() {
    request = new AccessTokenRequest(Verb.POST, "http://localhost:3000/oauth/token");
    request.addCode(new AuthorizationCode("auth"));
    request.addCode(new AccessToken("hello", "world", 7200));
    assertFalse(request.isValid());
  }
}
