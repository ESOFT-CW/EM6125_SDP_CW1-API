package lk.esoft.EM6125_SDP_CW1API.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/audit")
public class AuditController {

    @Autowired
    private AuditServiceImpl auditService;


    @Autowired
    private ResponseDTO responseDTO;
    @GetMapping("/getAllAudits")
    public ResponseEntity<ResponseDTO> getAllAudits(@RequestAttribute String username) {

        try{
            //System.out.println(role);
            List<AuditSectionDTO> allAudits = auditService.getAllAudits();
            responseDTO.setCode(VarList.Created);
            responseDTO.setMessage("Success");
            responseDTO.setData(allAudits);
            return new ResponseEntity<>(responseDTO, HttpStatus.ACCEPTED);
        }catch (Exception e){
            responseDTO.setCode(VarList.Internal_Server_Error);
            responseDTO.setMessage(e.getMessage());
            responseDTO.setData(e);
            return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
