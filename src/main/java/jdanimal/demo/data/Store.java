package jdanimal.demo.data;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "stores")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Store extends BaseEntity{

    @Column(name = "store_name")
    private String name;
    @Column(name = "location_path")
    @Type(type="text")
    private String locationPath;
    @Column(name = "start_hour")
    private String startHour;
    @Column(name = "end_hour")
    private String endHour;
    @Column(name = "description")
    @Type(type="text")
    private String description;
    @ManyToOne
    private User user;
}
