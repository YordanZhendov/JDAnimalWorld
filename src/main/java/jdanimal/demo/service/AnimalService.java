package jdanimal.demo.service;

import jdanimal.demo.service.models.UserAnimalUploadModel;
import jdanimal.demo.service.views.AnimalViewModel;
import jdanimal.demo.service.views.UserProfileViewModel;

import java.util.List;

public interface AnimalService {
    List<AnimalViewModel> getAllAnimals();
    void uploadAnimal(UserAnimalUploadModel userAnimalUploadBindingModel, UserProfileViewModel userProfileInfo);


}
