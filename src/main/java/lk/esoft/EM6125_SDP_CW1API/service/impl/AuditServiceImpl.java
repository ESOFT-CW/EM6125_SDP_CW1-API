package lk.esoft.EM6125_SDP_CW1API.service.impl;

import lk.esoft.EM6125_SDP_CW1API.entity.AuditSection;
import lk.esoft.EM6125_SDP_CW1API.repository.AuditRepository;
import lk.esoft.EM6125_SDP_CW1API.service.AuditService;
import lk.esoft.EM6125_SDP_CW1API.util.VarList.VarList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@Transactional
public class AuditServiceImpl implements AuditService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AuditRepository auditRepository;

    public int saveAudit(String function,String message) {

        LocalDateTime myObj = LocalDateTime.now();
        AuditSection auditSection=new AuditSection(0,myObj.toString(),function,message);
        auditRepository.save(auditSection);
        return VarList.Created;

    }

}
