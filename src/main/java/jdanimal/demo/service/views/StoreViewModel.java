package jdanimal.demo.service.views;

import jdanimal.demo.data.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StoreViewModel {

    private String id;
    private String name;
    private String locationPath;
    private String startHour;
    private String endHour;
    private String description;
    private User user;
}
