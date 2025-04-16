package site.ithinkso.web.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AdminAuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Boolean isLoggedIn = (Boolean) session.getAttribute("ADMIN_LOGGED_IN");
            if (isLoggedIn != null && isLoggedIn) {
                return true;
            }
        }

        response.sendRedirect(request.getContextPath() + "/admin/login");
        return false;
    }
}