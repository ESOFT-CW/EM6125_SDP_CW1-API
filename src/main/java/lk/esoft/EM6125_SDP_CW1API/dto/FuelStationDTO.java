package lk.esoft.EM6125_SDP_CW1API.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FuelStationDTO {
    private Integer tid;
    private Integer vehicleRegNo;
    private String status;
    private Date tokenExpDate;
    private Integer requestQuota;
    private Date fillingTimeAndDate;
    private User usernameFk;
    private Payment pidFk;
    private FuelStation fuelStationFk;
}
