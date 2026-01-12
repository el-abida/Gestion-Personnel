package ma.ensate.rh.ensate_rh_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyDataDTO {
    private String month;
    private int value;
}
