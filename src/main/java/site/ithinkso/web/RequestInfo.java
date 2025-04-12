package site.ithinkso.web;

import java.util.Map;

public class RequestInfo {

  private String ip;
  private int port;
  private String path;
  private Map<String, String> headers;
  private String body;

  public RequestInfo(
      String ip,
      int port,
      String path,
      Map<String, String> headers
  ) {
    this.ip = ip;
    this.port = port;
    this.path = path;
    this.headers = headers;
  }

  // Getter 메서드들
  public String getIp() {
    return ip;
  }

  public int getPort() {
    return port;
  }

  public String getPath() {
    return path;
  }

  public Map<String, String> getHeaders() {
    return headers;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }
}
