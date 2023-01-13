package lk.esoft.EM6125_SDP_CW1API.service.impl;

import lk.esoft.EM6125_SDP_CW1API.service.AuditService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    public List<AuditSectionDTO> getAllAudits() {
        List<AuditSection> auditSections=auditRepository.findAll();
        return modelMapper.map(auditSections, new TypeToken<ArrayList<AuditSectionDTO>>() {
        }.getType());
    }

}
