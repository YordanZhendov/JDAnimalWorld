package jdanimal.demo.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "accessories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Accessory extends BaseEntity{

    @Column(name = "accessory_name")
    private String accessoryName;
    @Column(name = "available_till")
    private String availableTill;
    @Column(name = "days_used")
    private int daysUsed;
    @Column(name = "accessory_price")
    private BigDecimal accessoryPrice;
    @ManyToOne
    private User user;

}
