package lk.esoft.EM6125_SDP_CW1API.controller;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/fuelstation")
public class FuelStationController {
    @Autowired
    private ResponseDTO responseDTO;

    @Autowired
    private FuelStationService fuelStationService;

    @PutMapping("/requestFuel")
    public ResponseEntity<ResponseDTO> requestFuel(@RequestParam int quota, @RequestAttribute String username) {
        try {
            int res = fuelStationService.requestFuel(quota, username);
            if (res==202) {
                responseDTO.setCode(VarList.Accepted);
                responseDTO.setMessage("success");
                responseDTO.setData(null);
                return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
            } else if (res==404) {
                responseDTO.setCode(VarList.Not_Found);
                responseDTO.setMessage("Username Already Use");
                responseDTO.setData(null);
                return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
            } else {
                responseDTO.setCode(VarList.Bad_Gateway);
                responseDTO.setMessage("Error");
                responseDTO.setData(null);
                return new ResponseEntity<>(responseDTO, HttpStatus.BAD_GATEWAY);
            }
        } catch (Exception e) {
            responseDTO.setCode(VarList.Internal_Server_Error);
            responseDTO.setMessage(e.getMessage());
            responseDTO.setData(e);
            return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @PutMapping("/requestFuelStatusChange")
    public ResponseEntity<ResponseDTO> requestFuelStatusChange(@RequestParam String status, @RequestAttribute String username) {
        try {
            int res = fuelStationService.requestFuelStatusChange(status, username);
            if (res==202) {
                responseDTO.setCode(VarList.Accepted);
                responseDTO.setMessage("success");
                responseDTO.setData(null);
                return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
            } else if (res==404) {
                responseDTO.setCode(VarList.Not_Found);
                responseDTO.setMessage("Username Already Use");
                responseDTO.setData(null);
                return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
            } else {
                responseDTO.setCode(VarList.Bad_Gateway);
                responseDTO.setMessage("Error");
                responseDTO.setData(null);
                return new ResponseEntity<>(responseDTO, HttpStatus.BAD_GATEWAY);
            }
        } catch (Exception e) {
            responseDTO.setCode(VarList.Internal_Server_Error);
            responseDTO.setMessage(e.getMessage());
            responseDTO.setData(e);
            return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @GetMapping("/getAllFuelStationDetails")
    public ResponseEntity<ResponseDTO> getAllFuelStationDetails(@RequestAttribute String username) {

        try{
            List<FuelStationDTO> allFuelStationDetails = fuelStationService.getAllFuelStationDetails();
            responseDTO.setCode(VarList.Created);
            responseDTO.setMessage("Success");
            responseDTO.setData(allFuelStationDetails);
            return new ResponseEntity<>(responseDTO, HttpStatus.ACCEPTED);
        }catch (Exception e){
            responseDTO.setCode(VarList.Internal_Server_Error);
            responseDTO.setMessage(e.getMessage());
            responseDTO.setData(e);
            return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}

