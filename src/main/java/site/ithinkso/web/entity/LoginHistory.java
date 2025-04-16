package site.ithinkso.web.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ip;
    private int port;
    private boolean correct;
    private String inputPassword;
    private LocalDateTime timestamp;

    public LoginHistory(String ip, int port, boolean correct, String inputPassword) {
        this.ip = ip;
        this.port = port;
        this.correct = correct;
        this.inputPassword = inputPassword;
        this.timestamp = LocalDateTime.now();
    }
}
