package jdanimal.demo.service.impl;

import jdanimal.demo.data.Accessory;
import jdanimal.demo.service.models.UserAccessoryUploadModel;
import jdanimal.demo.data.User;
import jdanimal.demo.repository.AccessoryRepository;
import jdanimal.demo.repository.UserRepository;
import jdanimal.demo.service.AccessoryService;
import jdanimal.demo.service.UserService;
import jdanimal.demo.web.binding.UserAccessoryUploadBinding;
import jdanimal.demo.service.views.AccessoryViewModel;
import jdanimal.demo.service.views.UserProfileViewModel;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AccessoryServiceImpl implements AccessoryService {

    private final AccessoryRepository accessoryRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;

    @Override
    public Accessory upload(UserAccessoryUploadBinding userAccessoryUploadBinding, UserProfileViewModel byUsername) {

        UserAccessoryUploadModel mappedAccessory = this.modelMapper.map(userAccessoryUploadBinding, UserAccessoryUploadModel.class);
        Accessory accessory = this.modelMapper.map(mappedAccessory, Accessory.class);
        User currentUser = this.userRepository.findByUsername(byUsername.getUsername());
        accessory.setUser(currentUser);
        Accessory accessoryUploaded = this.accessoryRepository.saveAndFlush(accessory);
        updateAccessoryCash();
        return accessoryUploaded;
    }

    @Override
    public boolean removeAccessoryById(String id) {
        try {
            Accessory accessoryById = this.accessoryRepository.findAccessoryById(id);
            this.userService.removeLikedAccessoryFromUsers(accessoryById);
            this.accessoryRepository.deleteById(id);
            updateAccessoryCash();
            return true;
        }catch (Exception e){
            return false;
        }

    }

    @Override
    public List<AccessoryViewModel> getAllAccessories() {
        return this.accessoryRepository.getAccessories()
                .stream()
                .map(accessory -> this.modelMapper.map(accessory, AccessoryViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public boolean saveUrlAccessory(String id, String replaceFileName) {
        try {
            Accessory accessoryById = this.accessoryRepository.findAccessoryById(id);
            accessoryById.setUrlAccessoryPhoto("https://jdanimalsworld.s3.eu-central-1.amazonaws.com/" + replaceFileName);
            this.accessoryRepository.saveAndFlush(accessoryById);
            return true;
        }catch (Exception e){
            return false;
        }
    }


    @Override
    public boolean addLikedAccessoryToTheCurrentUser(String id, String currentUserName) {
        try {
            User byUsername = this.userRepository.findByUsername(currentUserName);
            Accessory accessoryById = this.accessoryRepository.findAccessoryById(id);

            byUsername.getLikedAccessories().add(accessoryById);
            accessoryById.getUsers().add(byUsername);

            this.userRepository.saveAndFlush(byUsername);
            this.accessoryRepository.saveAndFlush(accessoryById);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean disLikedAccessoryToTheCurrentUser(String id, String currentUserName) {
        try {
            User byUsername = this.userRepository.findByUsername(currentUserName);
            Accessory accessoryById = this.accessoryRepository.findAccessoryById(id);
            byUsername.getLikedAccessories().remove(accessoryById);
            accessoryById.getUsers().remove(byUsername);
            this.userRepository.saveAndFlush(byUsername);
            this.accessoryRepository.saveAndFlush(accessoryById);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean updateAccessoryCash() {
        try {
           this.accessoryRepository.findAll();
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
