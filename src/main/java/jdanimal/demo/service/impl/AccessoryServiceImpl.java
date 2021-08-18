package jdanimal.demo.service.impl;

import jdanimal.demo.data.Accessory;
import jdanimal.demo.data.DTO.UserAccessoryUploadDTO;
import jdanimal.demo.data.User;
import jdanimal.demo.repository.AccessoryRepository;
import jdanimal.demo.repository.UserRepository;
import jdanimal.demo.service.AccessoryService;
import jdanimal.demo.service.models.UserAccessoryUploadModel;
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
        List<AccessoryViewModel> accessoryViewModelList = this.accessoryRepository.getAccessories()
                .stream()
                .map(accessory -> this.modelMapper.map(accessory, AccessoryViewModel.class))
                .collect(Collectors.toList());
        return accessoryViewModelList;
    }

}
