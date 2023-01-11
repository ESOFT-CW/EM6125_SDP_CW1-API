package lk.esoft.EM6125_SDP_CW1API.controller;

import lk.esoft.EM6125_SDP_CW1API.dto.FuelTokenResponseDTO;
import lk.esoft.EM6125_SDP_CW1API.dto.ResponseDTO;
import lk.esoft.EM6125_SDP_CW1API.util.VarList.VarList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("api/v1/fueltoken")
public class FuelTokenController {

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

}
