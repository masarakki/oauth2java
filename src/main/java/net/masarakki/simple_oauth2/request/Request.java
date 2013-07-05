package net.masarakki.simple_oauth2.request;

import java.io.IOException;

import net.arnx.jsonic.JSON;
import net.masarakki.simple_oauth2.models.Verb;
import net.masarakki.simple_oauth2.request.exceptions.BadRequestException;
import net.masarakki.simple_oauth2.request.exceptions.ForbiddenException;
import net.masarakki.simple_oauth2.request.exceptions.HttpException;
import net.masarakki.simple_oauth2.request.exceptions.NotFoundException;
import net.masarakki.simple_oauth2.request.exceptions.UnauthorizedException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

class Request {
  protected final Verb verb;
  protected final String url;
  protected final HttpUriRequest request;
  protected final HttpParams http_params;
  @SuppressWarnings("rawtypes")
  protected Class json_class;

  /**
   * 
   * @param request method
   * @param endpoint url
   * @param json container
   */
  @SuppressWarnings("rawtypes")
  public Request(Verb verb, String url, Class json_class) {
    this.verb = verb;
    this.url = url;
    this.request = buildRequest(verb, url);
    this.http_params = new BasicHttpParams();
    this.json_class = json_class;
  }

  protected HttpUriRequest buildRequest(Verb verb, String url) {
    if (verb.equals(Verb.GET)) {
      return new HttpGet(url);
    } else if (verb.equals(Verb.POST)) {
      return new HttpPost(url);
    } else if (verb.equals(Verb.PUT)) {
      return new HttpPut(url);
    } else if (verb.equals(Verb.DELETE)) {
      return new HttpDelete(url);
    }
    return null;
  }

  public Request addParam(String param, Object value) {
    this.http_params.setParameter(param, value);
    return this;
  }

  public boolean hasParam(String param) {
    return getParam(param) != null;
  }

  public Object getParam(String param) {
    return this.http_params.getParameter(param);
  }

  public HttpResponse send() throws ClientProtocolException, IOException {
    this.request.setParams(this.http_params);
    DefaultHttpClient client = new DefaultHttpClient();
    return client.execute(this.request);
  }

  @SuppressWarnings("unchecked")
  public <T> T getJson() throws ClientProtocolException, IOException, HttpException {
    HttpResponse response = this.send();
    int status_code = response.getStatusLine().getStatusCode();

    switch (status_code) {
    case 400:
      throw new BadRequestException();
    case 401:
      throw new UnauthorizedException();
    case 403:
      throw new ForbiddenException();
    case 404:
      throw new NotFoundException();
    }
    return (T) JSON.decode(response.getEntity().getContent(), this.json_class);
  }

  protected HttpParams getHttpParams() {
    return this.http_params;
  }

  public boolean isValid() {
    return true;
  }
}
