package jdanimal.demo.service;

import jdanimal.demo.data.DTO.EncyclopediaAnimalBinding;
import jdanimal.demo.service.views.EncyclopediaAnimalViewModel;

import java.util.List;

public interface EncyclopediaService {
    void saveInDB(EncyclopediaAnimalBinding encyclopediaAnimalBinding);
    List<EncyclopediaAnimalViewModel> findAllAnimalsInEncyclopedia();
    void removeAnimalFromEncyclopedia(String id);
    List<EncyclopediaAnimalViewModel> animalsFilterbyName(String type);
}
