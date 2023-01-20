package lk.esoft.EM6125_SDP_CW1API.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FuelStationDTO {
    private Integer fid;
    private String stationName;
    private String city;
    private String district;
    private int max_limit;
    private int available_limit;
    private int customer_requested_limit;
    private String status;
    private int station_requested_limit;
    private String username_fk;
}

