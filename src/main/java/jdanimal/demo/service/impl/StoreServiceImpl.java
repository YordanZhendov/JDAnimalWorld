package jdanimal.demo.service.impl;

import jdanimal.demo.data.DTO.UserStoreUploadDTO;
import jdanimal.demo.data.Store;
import jdanimal.demo.data.User;
import jdanimal.demo.repository.StoreRepository;
import jdanimal.demo.repository.UserRepository;
import jdanimal.demo.service.StoreService;
import jdanimal.demo.service.UserService;
import jdanimal.demo.service.models.UserStoreUploadModel;
import jdanimal.demo.service.views.StoreViewModel;
import jdanimal.demo.service.views.UserProfileViewModel;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public void uploadStore(UserStoreUploadModel userStoreUploadModel, String username) {
        String[] split = userStoreUploadModel.getLocationPath().split("\\s+");
        String[] split1 = split[1].split("\"");

        UserStoreUploadDTO mappedStore = this.modelMapper.map(userStoreUploadModel, UserStoreUploadDTO.class);
        Store mappedStoreEntity = this.modelMapper.map(mappedStore, Store.class);
        User byUsername = this.userRepository.findByUsername(username);
        mappedStoreEntity.setUser(byUsername);
        mappedStoreEntity.setLocationPath(split1[1]);

        this.storeRepository.saveAndFlush(mappedStoreEntity);



    }

    @Override
    public List<StoreViewModel> getAllStores() {
        return this.storeRepository.findAll()
                .stream()
                .map(store -> this.modelMapper.map(store, StoreViewModel.class))
                .collect(Collectors.toList());
    }

}
