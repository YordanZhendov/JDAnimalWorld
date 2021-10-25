package jdanimal.demo.service.impl;

import jdanimal.demo.data.Animal;
import jdanimal.demo.service.models.UserAnimalUploadModel;
import jdanimal.demo.data.User;
import jdanimal.demo.repository.AnimalRepository;
import jdanimal.demo.repository.UserRepository;
import jdanimal.demo.service.AnimalService;
import jdanimal.demo.service.UserService;
import jdanimal.demo.web.binding.UserAnimalUploadBinding;
import jdanimal.demo.service.views.AnimalViewModel;
import jdanimal.demo.service.views.UserProfileViewModel;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AnimalServiceImpl implements AnimalService {

    private final AnimalRepository animalRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;

    @Override
    public List<AnimalViewModel> getAllAnimals() {
        return this.animalRepository.findAllAnimals()
                .stream()
                .map(animal -> this.modelMapper.map(animal, AnimalViewModel.class))
                .collect(Collectors.toList());

    }

    @Override
    public void uploadAnimal(UserAnimalUploadBinding userAnimalUploadBinding, UserProfileViewModel userProfileInfo){
        UserAnimalUploadModel mappedAnimal = this.modelMapper.map(userAnimalUploadBinding, UserAnimalUploadModel.class);
        Animal mappedAnimalEntity = this.modelMapper.map(mappedAnimal, Animal.class);
        User byUsername = this.userRepository.findByUsername(userProfileInfo.getUsername());
        mappedAnimalEntity.setUser(byUsername);
        this.animalRepository.saveAndFlush(mappedAnimalEntity);
        this.animalRepository.findAll();
    }

    @Override
    public void removeAnimal(String id) {
        Animal animalById = animalRepository.findAnimalById(id);
        this.userService.removeAnimalFromUsers(animalById);
        this.animalRepository.deleteById(id);
        this.animalRepository.findAll();


    }

    @Override
    public void saveUrlAnimal(String id, String replaceFileName) {
        Animal animalById = animalRepository.findAnimalById(id);
        animalById.setUrlAnimalPhoto("https://jdanimalsworld.s3.eu-central-1.amazonaws.com/" + replaceFileName);
        animalRepository.saveAndFlush(animalById);
    }

    @Override
    public void addLikedAnimalTotheCurrentUser(String id, String currentUserName) {
        User byUsername = userRepository.findByUsername(currentUserName);
        Animal animalById = animalRepository.findAnimalById(id);
        byUsername.getLikedAnimals().add(animalById);
        animalById.getUsers().add(byUsername);
        userRepository.saveAndFlush(byUsername);
        animalRepository.saveAndFlush(animalById);
    }

    @Override
    public void disLikedAnimalTotheCurrentUser(String id, String currentUserName) {
        User byUsername = userRepository.findByUsername(currentUserName);
        Animal animalById = animalRepository.findAnimalById(id);
        byUsername.getLikedAnimals().remove(animalById);
        animalById.getUsers().remove(byUsername);
        userRepository.saveAndFlush(byUsername);
        animalRepository.saveAndFlush(animalById);
    }
}
