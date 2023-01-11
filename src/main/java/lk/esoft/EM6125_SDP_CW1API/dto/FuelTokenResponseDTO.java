package lk.esoft.EM6125_SDP_CW1API.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class FuelTokenResponseDTO {
    private byte[]qrString;
    private int availableQuota;
    private int fullQuota=20;
}

