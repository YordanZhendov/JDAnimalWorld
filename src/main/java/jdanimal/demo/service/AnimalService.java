package jdanimal.demo.service;

import jdanimal.demo.web.binding.UserAnimalUploadBinding;
import jdanimal.demo.service.views.AnimalViewModel;
import jdanimal.demo.service.views.UserProfileViewModel;

import java.util.List;

public interface AnimalService {
    List<AnimalViewModel> getAllAnimals();
    void uploadAnimal(UserAnimalUploadBinding userAnimalUploadBindingModel, UserProfileViewModel userProfileInfo);
    void removeAnimal(String id);
    void saveUrlAnimal(String id, String replaceFileName);
    void addLikedAnimalTotheCurrentUser(String id, String currentUserName);
    void disLikedAnimalTotheCurrentUser(String id, String currentUserName);
    void updateAnimalCash();
}
