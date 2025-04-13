package site.ithinkso.web;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class RequestInfoController {

    @GetMapping
    public String getRequestInfo(HttpServletRequest request, Model model) {
        RequestInfo info = createBasicRequestInfo(request);
        model.addAttribute("info", info);
        return "request-info";
    }

    @PostMapping
    public String postRequestInfo(HttpServletRequest request, Model model) throws IOException {
        RequestInfo info = createBasicRequestInfo(request);

        info.setBody(
                request.getReader()
                        .lines()
                        .collect(Collectors.joining(System.lineSeparator()))
        );

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
}
