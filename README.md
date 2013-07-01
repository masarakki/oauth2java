# oauth2java

simple oauth2 client for java

## Usage

(imaginaly)

### define your own provider

```java
class MyServiceApi extends Api {
  protected final String base_url = "https://example.com/oauth";
  public String getAuthorizationUrl() {
    return base_url + "/authorize";
  }

  public String getAccessTokenUrl() {
    return base_url + "/token";
  }
}

class MyClient extends OAuthClient {
  public MyClient {
    super().api(MyServiceApi.class).key("aaa").secret("bbb").callback("http://localhost/callback");
  }
}
```

### get access_token

```java
MyServiceClient client = new MyServiceClient();
client.getAuthorizationUrl();

//=== callback from provider

AuthorizationCode code = new AuthorizationCode(request.getParam("code"));
AccessToken access_token = client.getAccessToken(code);
AccessToken new_access_token = client.getAccessToken(access_token); // refresh

```
