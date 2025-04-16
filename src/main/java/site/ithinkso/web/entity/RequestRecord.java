package site.ithinkso.web.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.ithinkso.web.model.RequestInfo;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequestRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ip;
    private int port;
    private String path;

    @Lob
    private String headers;

    @Lob
    private String body;

    private LocalDateTime timestamp;

    public static RequestRecord from(RequestInfo requestInfo) {
        return new RequestRecord(
                requestInfo.getIp(),
                requestInfo.getPort(),
                requestInfo.getPath(),
                requestInfo.getHeaders().toString(),
                requestInfo.getBody(),
                requestInfo.getTimestamp()
        );
    }

    public RequestRecord(String ip, int port, String path, String headers, String body, LocalDateTime timestamp) {
        this.ip = ip;
        this.port = port;
        this.path = path;
        this.headers = headers;
        this.body = body;
        this.timestamp = timestamp;
    }

}