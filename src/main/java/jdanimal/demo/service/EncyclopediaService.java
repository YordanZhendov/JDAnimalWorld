package jdanimal.demo.service;

import jdanimal.demo.web.binding.EncyclopediaAnimalBinding;
import jdanimal.demo.service.views.EncyclopediaAnimalViewModel;

import java.util.List;

public interface EncyclopediaService {
    List<EncyclopediaAnimalViewModel> findAllAnimalsInEncyclopedia();
    boolean updateEncyclopedia();

    List<EncyclopediaAnimalViewModel> animalsFilterByType(String type);
    boolean saveInDB(EncyclopediaAnimalBinding encyclopediaAnimalBinding);
    boolean removeAnimalFromEncyclopedia(String id);
}
