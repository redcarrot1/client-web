package site.ithinkso.web.reposistory;

import org.springframework.data.jpa.repository.JpaRepository;
import site.ithinkso.web.entity.RequestRecord;

import java.util.List;

public interface RequestRecordRepository extends JpaRepository<RequestRecord, Long> {

    List<RequestRecord> findByIp(String ip);

    List<RequestRecord> findByPort(int port);

    List<RequestRecord> findByIpAndPort(String ip, int port);
}
