package lk.esoft.EM6125_SDP_CW1API.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private AuditServiceImpl auditService;
    @Autowired
    private PaymentRepository paymentRepository;

    public int changePaymentStatus(int pid,String status){
        if (paymentRepository.existsById(pid)) {
            paymentRepository.changePaymentStatus(pid,status);
            auditService.saveAudit("changePaymentStatus","PASS:Change Payment Status"+status);
            return VarList.Accepted;
        } else {
            auditService.saveAudit("changePaymentStatus","FAIL:Change Payment Status"+status);
            return VarList.Not_Found;

        }


    }

}
