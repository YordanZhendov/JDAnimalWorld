package jdanimal.demo.service.impl;

import jdanimal.demo.service.models.UserStoreUploadModel;
import jdanimal.demo.data.Store;
import jdanimal.demo.data.User;
import jdanimal.demo.repository.StoreRepository;
import jdanimal.demo.repository.UserRepository;
import jdanimal.demo.service.StoreService;
import jdanimal.demo.web.binding.UserStoreUploadBinding;
import jdanimal.demo.service.views.StoreViewModel;
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
    public void uploadStore(UserStoreUploadBinding userStoreUploadBinding, String username) {
        String[] split = userStoreUploadBinding.getLocationPath().split("\\s+");
        String[] split1 = split[1].split("\"");

        UserStoreUploadModel mappedStore = this.modelMapper.map(userStoreUploadBinding, UserStoreUploadModel.class);
        Store mappedStoreEntity = this.modelMapper.map(mappedStore, Store.class);
        User byUsername = this.userRepository.findByUsername(username);
        mappedStoreEntity.setUser(byUsername);
        mappedStoreEntity.setLocationPath(split1[1]);

        this.storeRepository.saveAndFlush(mappedStoreEntity);
        this.storeRepository.findAll();

    }

    @Override
    public List<StoreViewModel> getAllStores() {
        return this.storeRepository.getAllStores()
                .stream()
                .map(store -> this.modelMapper.map(store, StoreViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<StoreViewModel> getAllStoresByUser(String username) {
        List<StoreViewModel> allStores = getAllStores();
        return allStores.stream().filter(storeViewModel -> storeViewModel.getUser().getUsername().equals(username)).collect(Collectors.toList());
    }

    @Override
    public void removeStore(String id) {
        this.storeRepository.deleteById(id);
        this.storeRepository.findAll();
    }

}
