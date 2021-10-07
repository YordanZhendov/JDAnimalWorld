package jdanimal.demo.service.impl;

import jdanimal.demo.data.Animal;
import jdanimal.demo.data.DTO.UserAnimalUploadDTO;
import jdanimal.demo.data.User;
import jdanimal.demo.repository.AnimalRepository;
import jdanimal.demo.repository.UserRepository;
import jdanimal.demo.service.AnimalService;
import jdanimal.demo.service.models.UserAnimalDeleteModel;
import jdanimal.demo.service.models.UserAnimalUploadModel;
import jdanimal.demo.service.views.AnimalViewModel;
import jdanimal.demo.service.views.UserProfileViewModel;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AnimalServiceImpl implements AnimalService {

    private final AnimalRepository animalRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<AnimalViewModel> getAllAnimals() {
        return this.animalRepository.findAllAnimals()
                .stream()
                .map(animal -> this.modelMapper.map(animal, AnimalViewModel.class))
                .collect(Collectors.toList());

    }

    @Override
    public void uploadAnimal(UserAnimalUploadModel userAnimalUploadModel, UserProfileViewModel userProfileInfo){
        UserAnimalUploadDTO mappedAnimal = this.modelMapper.map(userAnimalUploadModel, UserAnimalUploadDTO.class);
        Animal mappedAnimalEntity = this.modelMapper.map(mappedAnimal, Animal.class);
        User byUsername = this.userRepository.findByUsername(userProfileInfo.getUsername());
        mappedAnimalEntity.setUser(byUsername);
        this.animalRepository.saveAndFlush(mappedAnimalEntity);
    }

    @Override
    public void removeAnimal(String id) {
        this.animalRepository.deleteById(id);
    }

    @Override
    public void saveUrlAnimal(String id, String replaceFileName) {
        Animal animalById = animalRepository.findAnimalById(id);
        animalById.setUrlAnimalPhoto("https://jdanimalsworld.s3.eu-central-1.amazonaws.com/" + replaceFileName);
        animalRepository.saveAndFlush(animalById);
    }
}
