package jdanimal.demo.service.views;

import jdanimal.demo.data.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccessoryViewModel {

    private String id;
    private String accessoryName;
    private String availableTill;
    private int daysUsed;
    private BigDecimal accessoryPrice;
    private String urlAccessoryPhoto;
    private User user;
}
