package site.ithinkso.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import site.ithinkso.web.entity.RequestRecord;
import site.ithinkso.web.reposistory.LoginHistoryRepository;
import site.ithinkso.web.reposistory.RequestRecordRepository;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("admin/histories")
@RequiredArgsConstructor
public class HistoryController {

    private final RequestRecordRepository requestRecordRepository;
    private final LoginHistoryRepository loginHistoryRepository;

    @GetMapping("login")
    public String loginHistory(Model model) {
        model.addAttribute("records", loginHistoryRepository.findAll());
        return "admin/history/login";
    }

    @GetMapping("request")
    public String requestHistory(@RequestParam(value = "ip", required = false) String ip,
                                 @RequestParam(value = "port", required = false) Integer port,
                                 Model model) {
        List<RequestRecord> records = requestRecordRepository.findAll();
        if (ip != null && !ip.isEmpty()) {
            records = records.stream()
                    .filter(r -> r.getIp().contains(ip))
                    .collect(Collectors.toList());
        }
        if (port != null) {
            records = records.stream()
                    .filter(r -> r.getPort() == port)
                    .collect(Collectors.toList());
        }

        model.addAttribute("records", records);
        return "admin/history/request";
    }
}
