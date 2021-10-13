package jdanimal.demo.data.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserStoreUploadDTO {

    private String name;
    private String locationPath;
    private String startHour;
    private String endHour;
    private String description;
}
