package jdanimal.demo.service;

import jdanimal.demo.data.Animal;
import jdanimal.demo.web.binding.UserAnimalUploadBinding;
import jdanimal.demo.service.views.AnimalViewModel;
import jdanimal.demo.service.views.UserProfileViewModel;

import java.util.List;

public interface AnimalService {
    List<AnimalViewModel> getAllAnimals();
    boolean updateAnimalCash();

    Animal uploadAnimal(UserAnimalUploadBinding userAnimalUploadBindingModel,UserProfileViewModel userProfileInfo);
    boolean removeAnimal(String id);
    boolean saveUrlAnimal(String id, String replaceFileName);

    boolean addLikedAnimalToTheCurrentUser(String id, String currentUserName);
    boolean disLikedAnimalToTheCurrentUser(String id, String currentUserName);

}
