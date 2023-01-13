package lk.esoft.EM6125_SDP_CW1API.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("api/v1/payment")
public class PaymentController {


    @Autowired
    private PaymentService paymentService;

    @Autowired
    private ResponseDTO responseDTO;

    @PutMapping("/changePaymentStatus")
    public ResponseEntity<ResponseDTO> changePaymentStatus(@RequestParam int pid, @RequestParam String status) {
        try {
            paymentService.changePaymentStatus(pid, status);
            responseDTO.setCode(VarList.Created);
            responseDTO.setMessage("Success");
            responseDTO.setData(null);
            return new ResponseEntity<>(responseDTO, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            responseDTO.setCode(VarList.Internal_Server_Error);
            responseDTO.setMessage(e.getMessage());
            responseDTO.setData(e);
            return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }



}
