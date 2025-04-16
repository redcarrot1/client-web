package site.ithinkso.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import site.ithinkso.web.entity.RequestRecord;
import site.ithinkso.web.model.RequestInfo;
import site.ithinkso.web.reposistory.RequestRecordRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class RequestInfoController {

    private static final int MAX_RECORDS = 2000;
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final SimpMessagingTemplate messagingTemplate;
    private final RequestRecordRepository requestRecordRepository;

    @GetMapping("live")
    public String liveRequestInfoPage() {
        return "request-info-live";
    }

    @GetMapping
    public String getRequestInfo(HttpServletRequest request, Model model) {
        RequestInfo info = createBasicRequestInfo(request);
        saveRequestRecord(info);
        broadcastClientInfo(info.getIp(), info.getPort(), info.getTimestamp());

        model.addAttribute("info", info);
        return "request-info";
    }

    @PostMapping
    public String postRequestInfo(HttpServletRequest request, Model model) throws IOException {
        RequestInfo info = createBasicRequestInfo(request);
        info.setBody(request.getReader()
                .lines()
                .collect(Collectors.joining(System.lineSeparator()))
        );
        saveRequestRecord(info);
        broadcastClientInfo(info.getIp(), info.getPort(), info.getTimestamp());

        model.addAttribute("info", info);
        return "request-info";
    }

    private RequestInfo createBasicRequestInfo(HttpServletRequest request) {
        String ip = Optional.ofNullable(request.getHeader("X-Forwarded-For"))
                .orElse(request.getRemoteAddr());
        int port = request.getRemotePort();
        String path = request.getRequestURI();

        Map<String, String> headers = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            headers.put(name, request.getHeader(name));
        }

        return new RequestInfo(ip, port, path, headers);
    }

    private void saveRequestRecord(RequestInfo requestInfo) {
        requestRecordRepository.save(RequestRecord.from(requestInfo));

        if (requestRecordRepository.count() > MAX_RECORDS) {
            Optional<RequestRecord> oldest = requestRecordRepository.findAll().stream()
                    .min(Comparator.comparing(RequestRecord::getTimestamp));
            oldest.ifPresent(requestRecordRepository::delete);
        }
    }

    private void broadcastClientInfo(String ip, int port, LocalDateTime timestamp) {
        Map<String, String> payload = new HashMap<>();
        payload.put("ip", ip);
        payload.put("port", String.valueOf(port));
        payload.put("timestamp", timestamp.format(dateTimeFormatter));
        messagingTemplate.convertAndSend("/topic/clients", payload);
    }
}
