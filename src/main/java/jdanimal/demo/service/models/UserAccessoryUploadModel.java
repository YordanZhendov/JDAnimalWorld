package jdanimal.demo.service.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAccessoryUploadModel {

    private String id;
    private String accessoryName;
    private String availableTill;
    private int daysUsed;
    private BigDecimal accessoryPrice;
    private String urlAccessoryPhoto;
    private String description;
}
