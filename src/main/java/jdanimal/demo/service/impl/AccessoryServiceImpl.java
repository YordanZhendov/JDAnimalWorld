package jdanimal.demo.service.impl;

import jdanimal.demo.data.Accessory;
import jdanimal.demo.data.Animal;
import jdanimal.demo.data.DTO.UserAccessoryUploadDTO;
import jdanimal.demo.data.User;
import jdanimal.demo.repository.AccessoryRepository;
import jdanimal.demo.repository.UserRepository;
import jdanimal.demo.service.AccessoryService;
import jdanimal.demo.service.UserService;
import jdanimal.demo.service.models.UserAccessoryUploadModel;
import jdanimal.demo.service.views.AccessoryViewModel;
import jdanimal.demo.service.views.UserProfileViewModel;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AccessoryServiceImpl implements AccessoryService {

    private final AccessoryRepository accessoryRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;

    @Override
    public void upload(UserAccessoryUploadModel userAccessoryUploadModel, UserProfileViewModel byUsername) {
        UserAccessoryUploadDTO mappedAccessory = this.modelMapper.map(userAccessoryUploadModel, UserAccessoryUploadDTO.class);
        Accessory accessory = this.modelMapper.map(mappedAccessory, Accessory.class);
        User currentUser = this.userRepository.findByUsername(byUsername.getUsername());
        accessory.setUser(currentUser);
        this.accessoryRepository.saveAndFlush(accessory);
    }

    @Override
    public List<AccessoryViewModel> getAllAccessories() {
        return this.accessoryRepository.getAccessories()
                .stream()
                .map(accessory -> this.modelMapper.map(accessory, AccessoryViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public void saveUrlAccessory(String id, String replaceFileName) {
        Accessory accessoryById = accessoryRepository.findAccessoryById(id);
        accessoryById.setUrlAccessoryPhoto("https://jdanimalsworld.s3.eu-central-1.amazonaws.com/" + replaceFileName);
        accessoryRepository.saveAndFlush(accessoryById);
    }

    @Override
    public void removeAccessory(String id) {
        Accessory accessoryById = accessoryRepository.findAccessoryById(id);
        this.userService.removeAccessoryFromUsers(accessoryById);
        this.accessoryRepository.deleteById(id);
    }

    @Override
    public void addLikedAccessoryTotheCurrentUser(String id, String currentUserName) {
        User byUsername = userRepository.findByUsername(currentUserName);
        Accessory accessoryById = accessoryRepository.findAccessoryById(id);
        byUsername.getLikedAccessories().add(accessoryById);
        userRepository.saveAndFlush(byUsername);
    }

    @Override
    public void disLikedAccessoryTotheCurrentUser(String id, String currentUserName) {
        User byUsername = userRepository.findByUsername(currentUserName);
        Accessory accessoryById = accessoryRepository.findAccessoryById(id);
        byUsername.getLikedAccessories().remove(accessoryById);
        userRepository.saveAndFlush(byUsername);
    }

}
