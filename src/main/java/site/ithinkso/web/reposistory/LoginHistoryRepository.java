package site.ithinkso.web.reposistory;

import org.springframework.data.jpa.repository.JpaRepository;
import site.ithinkso.web.entity.LoginHistory;

public interface LoginHistoryRepository extends JpaRepository<LoginHistory, Long> {
}
