# oauth2java

simple oauth2 client for java

## Usage

(imaginaly)

### define your own provider

```java
class MyService extends OAuthService {
  protected String api_host = "https://api.example.com";
  protected String auth_host = "https://auth.example.com";

  public MyClient {
    super().api(MyServiceApi.class).key("aaa").secret("bbb").callback("http://localhost/callback");
  }

  @Override
  public String getAuthorizationUrl() {
    return auth_host + "/oauth/authorize";
  }

  @Override
  public String getAccessTokenUrl() {
    return auth_host + "/oauth/token";
  }

  @Override
  public Verb getAccessTokenVerb() {
    return Verb.POST;
  }


  /**
   * request
   *   GET https://example.com/api/v1/user
   *
   * response
   *   {"user_name": "masarakki", "uid": 12345678}
   */
  public UserInfo getUserInfo() {
    return request(Verb.GET, "https://example.com/api/v1/user", UserInfo.class);
  }
}

class UserInfo {
  protected String user_name;
  protected Integer uid;
}
```

### how to use service;

```java
MyService service = new MyService();
client.getAuthorizationUrl();

//=== callback from provider

AuthorizationCode code = new AuthorizationCode(request.getParam("code"));
client.auth(code);
UserInfo user_info = client.getUserInfo();

// store AccessToken
AcessToken access_token = client.getAccessToken();
object_output_stream.writeObject(access_token);

// restore AccessToken
AcecssToken access_token = (AccessToken) object_input_stream.readObject();
client.auth(access_token);

client.getUserInfo(); // Access Token will auto-refresh if expired.


```
