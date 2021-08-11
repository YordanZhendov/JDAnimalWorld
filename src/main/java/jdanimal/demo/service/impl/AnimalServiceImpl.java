package jdanimal.demo.service.impl;

import jdanimal.demo.data.Animal;
import jdanimal.demo.repository.AnimalRepository;
import jdanimal.demo.service.AnimalService;
import jdanimal.demo.service.views.AnimalViewModel;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AnimalServiceImpl implements AnimalService {

    private final AnimalRepository animalRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<AnimalViewModel> getAllAnimals() {
        return animalRepository.findAllAnimals()
                .stream()
                .map(animal -> modelMapper.map(animal, AnimalViewModel.class))
                .collect(Collectors.toList());

    }

    @Override
    public void deleteByNameOfAnimal(String name) {
        animalRepository.deleteByNameOfAnimal(name);
    }
}
