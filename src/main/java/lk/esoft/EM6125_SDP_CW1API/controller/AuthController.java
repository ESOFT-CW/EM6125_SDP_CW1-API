package lk.esoft.EM6125_SDP_CW1API.controller;

/**
 * @author Udara San
 * @TimeStamp 11:43 AM | 11/9/2022 | 2022
 * @ProjectDetails ecom-api
 */

import lk.esoft.EM6125_SDP_CW1API.dto.AuthDTO;
import lk.esoft.EM6125_SDP_CW1API.dto.ResponseDTO;
import lk.esoft.EM6125_SDP_CW1API.dto.UserDTO;
import lk.esoft.EM6125_SDP_CW1API.service.Impl.UserServiceImpl;
import lk.esoft.EM6125_SDP_CW1API.util.JwtUtil;
import lk.esoft.EM6125_SDP_CW1API.util.VarListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private ResponseDTO responseDTO;


    @PostMapping("/authenticate")
    public ResponseEntity<ResponseDTO> authenticate(@RequestBody UserDTO userDTO) throws Exception{
        System.out.println(userDTO);
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userDTO.getUsername(),
                            userDTO.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }

        final UserDetails userDetails
                = userService.loadUserByUsername(userDTO.getUsername());

        final UserDTO userDTO2=userService.loadUserDetailsByUsername(userDTO.getUsername());

        final String token =
                jwtUtil.generateToken(userDTO2);

        if (token!=null  && !token.isEmpty() ){

            UserDTO userDTO1=userService.loadUserDetailsByUsername(userDTO.getUsername());
            AuthDTO authDTO = new AuthDTO();
            authDTO.setUsername(userDTO1.getUsername());
            authDTO.setToken(token);

            responseDTO.setCode(VarListUtil.RSP_SUCCESS);
            responseDTO.setMessage("Success");
            responseDTO.setContent(authDTO);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        }
        responseDTO.setCode(VarListUtil.RSP_ERROR);
        responseDTO.setMessage("User Name Not Found");
        responseDTO.setContent(null);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
