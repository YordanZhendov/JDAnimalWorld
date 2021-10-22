package jdanimal.demo.service;

import jdanimal.demo.web.binding.UserStoreUploadBinding;
import jdanimal.demo.service.views.StoreViewModel;

import java.util.List;

public interface StoreService {
    void uploadStore(UserStoreUploadBinding userStoreUploadBinding, String username);
    List<StoreViewModel> getAllStores();
    List<StoreViewModel> getAllStoresByUser(String username);
    void removeStore(String id);
}
