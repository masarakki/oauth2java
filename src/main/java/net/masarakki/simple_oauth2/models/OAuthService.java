package net.masarakki.simple_oauth2.models;

/**
 * <pre>
 * class MyService extends OAuthService {
 *   protected String getAuthorizationUrl() {
 *     return &quot;https://example.com/oauth/authorize&quot;;
 *   }
 * 
 *   protected String getAccessTokenUrl() {
 *     return &quot;https://example.com/oauth/token&quot;;
 *   }
 * 
 *   protected Verb getAccessTokenVerb() {
 *     return Verb.POST;
 *   }
 * }
 * 
 * MyService service = new MyService();
 * service.key(&quot;API_KEY&quot;).secret(&quot;API_SECRET&quot;).callback(&quot;http://localhost/callback&quot;);
 * </pre>
 */
abstract class OAuthService {
  AccessToken access_token;

  /**
   *   
   */
  public OAuthService() {

  }

  /**
   * define the authorization endpoint
   * 
   * @return authorization endpoint
   */
  abstract String getAuthorizationUrl();

  /**
   * define the access_token endpoint
   * 
   * @return access_token endpopint
   */
  abstract String getAccessTokenUrl();

  /**
   * define access token verb
   * 
   * @return verb for access_token request (default GET)
   */
  protected Verb getAccessTokenVerb() {
    return Verb.GET;
  }

  public void auth(AuthorizationCode code) {
    new AccessTokenRequest(this.getAccessTokenVerb(), this.getAccessTokenUrl(), code);
  }

  public void auth(AccessToken token) {
    this.access_token = token;
  }
}
