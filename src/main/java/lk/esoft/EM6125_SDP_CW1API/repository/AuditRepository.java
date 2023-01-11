package lk.esoft.EM6125_SDP_CW1API.repository;

import lk.esoft.EM6125_SDP_CW1API.entity.AuditSection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditRepository extends JpaRepository<AuditSection,Integer> {
}
