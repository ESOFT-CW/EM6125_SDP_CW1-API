package lk.esoft.EM6125_SDP_CW1API.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuditSectionDTO {
    private Integer aids;
    private String dates;
    private String functions;
    private String messages;
}
