package site.ithinkso.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import site.ithinkso.web.entity.LoginHistory;
import site.ithinkso.web.reposistory.LoginHistoryRepository;

import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("admin")
@RequiredArgsConstructor
public class AdminController {

    private final LoginHistoryRepository loginHistoryRepository;

    @Value("${admin.password}")
    private String adminPassword;

    @GetMapping
    public String adminHome() {
        return "admin/home";
    }

    @GetMapping("login")
    public String loginForm() {
        return "admin/login";
    }

    @PostMapping("login")
    public String login(@RequestParam("password") String password, HttpServletRequest request, Model model) {
        String ip = Optional.ofNullable(request.getHeader("X-Forwarded-For"))
                .orElse(request.getRemoteAddr());
        int port = request.getRemotePort();

        if (Objects.equals(adminPassword, password)) {
            request.getSession().setAttribute("ADMIN_LOGGED_IN", true);
            loginHistoryRepository.save(new LoginHistory(ip, port, true, password));
            return "redirect:/admin";
        }

        loginHistoryRepository.save(new LoginHistory(ip, port, false, password));
        model.addAttribute("error", "비밀번호가 올바르지 않습니다.");
        return "admin/login";
    }

    @GetMapping("logout")
    public String logout(HttpServletRequest request) {
        request.getSession(false).invalidate();
        return "redirect:/admin/login";
    }
}