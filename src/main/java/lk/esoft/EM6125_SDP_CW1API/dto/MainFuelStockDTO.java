package lk.esoft.EM6125_SDP_CW1API.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MainFuelStockDTO {
    private int mfs_id;
    private String status;
    private int available_limit;
    private int requested_limit;
    private int main_stock;
}
