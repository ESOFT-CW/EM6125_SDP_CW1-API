package lk.esoft.EM6125_SDP_CW1API.controller;

import lk.esoft.EM6125_SDP_CW1API.dto.FuelTokenResponseDTO;
import lk.esoft.EM6125_SDP_CW1API.dto.ResponseDTO;
import lk.esoft.EM6125_SDP_CW1API.service.FuelTokenService;
import lk.esoft.EM6125_SDP_CW1API.util.VarList.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("api/v1/fueltoken")
public class FuelTokenController {
    @Autowired
    private ResponseDTO responseDTO;
    @Autowired
    private FuelTokenService fuelTokenService;

    @GetMapping("/getAllQRandDetails")
    public ResponseEntity<ResponseDTO> getAllQRandDetails(@RequestAttribute String username) {

        try{
            //System.out.println(role);
            FuelTokenResponseDTO fuelTokenResponseDTO = fuelTokenService.getAllQRandDetails(username);
            responseDTO.setCode(VarList.Created);
            responseDTO.setMessage("Success");
            responseDTO.setData(fuelTokenResponseDTO);
            return new ResponseEntity<>(responseDTO, HttpStatus.ACCEPTED);
        }catch (Exception e){
            responseDTO.setCode(VarList.Internal_Server_Error);
            responseDTO.setMessage(e.getMessage());
            responseDTO.setData(e);
            return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @PutMapping("/changeTokenStatus")
    public ResponseEntity<ResponseDTO> changeTokenStatus(@RequestParam int tid, @RequestParam String status) {
        try {
            fuelTokenService.changeTokenStatus(tid, status);
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
