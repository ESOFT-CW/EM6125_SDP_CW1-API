package lk.esoft.EM6125_SDP_CW1API.dto;
/**
 * @author Udara San
 * @TimeStamp 11:43 AM | 11/9/2022 | 2022
 * @ProjectDetails ecom-api
 */
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {
    private String password;
    private String roleCode;
    private String address;
    private String username;
    private String status;
    private String phoneNo1;
    private String phoneNo2;
    private String idPhoto;
    private String remarks;
    private String email;
    private String name;
}