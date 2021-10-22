package jdanimal.demo.service.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserStoreUploadModel {

    private String name;
    private String locationPath;
    private String startHour;
    private String endHour;
    private String description;
}
