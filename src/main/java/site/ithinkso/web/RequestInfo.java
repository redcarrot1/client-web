package site.ithinkso.web;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
public class RequestInfo {

    private String ip;
    private int port;
    private String path;
    private Map<String, String> headers;

    @Setter
    private String body;

    public RequestInfo(String ip, int port, String path, Map<String, String> headers) {
        this.ip = ip;
        this.port = port;
        this.path = path;
        this.headers = headers;
    }
}
