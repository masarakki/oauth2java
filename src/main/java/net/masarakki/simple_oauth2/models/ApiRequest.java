package net.masarakki.simple_oauth2.models;

import java.io.IOException;

import net.masarakki.simple_oauth2.request.Request;
import net.masarakki.simple_oauth2.request.exceptions.HttpException;
import net.masarakki.simple_oauth2.request.exceptions.UnauthorizedException;

import org.apache.http.client.ClientProtocolException;

abstract public class ApiRequest extends Request {

  @SuppressWarnings("rawtypes")
  public ApiRequest(Verb verb, String url, Class klass) {
    super(verb, url, klass);
  }

  public <T> T getJson() throws ClientProtocolException, IOException, HttpException {
    try {
      return super.getJson();
    } catch (UnauthorizedException e) {
      e.printStackTrace();
      return null;
    }
  }
}
