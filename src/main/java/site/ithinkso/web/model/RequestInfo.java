package site.ithinkso.web.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
public class RequestInfo {

    private String ip;
    private int port;
    private String path;
    private Map<String, String> headers;
    private LocalDateTime timestamp;

    @Setter
    private String body;

    public RequestInfo(String ip, int port, String path, Map<String, String> headers) {
        this.ip = ip;
        this.port = port;
        this.path = path;
        this.headers = headers;
        this.timestamp = LocalDateTime.now();
    }
}
