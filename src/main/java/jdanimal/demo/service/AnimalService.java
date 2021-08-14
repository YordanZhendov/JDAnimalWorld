package jdanimal.demo.service;

import jdanimal.demo.service.models.UserAnimalUploadModel;
import jdanimal.demo.service.views.AnimalViewModel;
import jdanimal.demo.service.views.UserProfileViewModel;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AnimalService {
    List<AnimalViewModel> getAllAnimals();
    void uploadAnimal(UserAnimalUploadModel userAnimalUploadBindingModel, UserProfileViewModel userProfileInfo);


}
