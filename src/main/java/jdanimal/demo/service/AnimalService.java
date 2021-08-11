package jdanimal.demo.service;

import jdanimal.demo.service.views.AnimalViewModel;

import java.util.List;

public interface AnimalService {
    List<AnimalViewModel> getAllAnimals();
    void deleteByNameOfAnimal(String name);

}
