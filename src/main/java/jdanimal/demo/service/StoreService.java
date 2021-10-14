package jdanimal.demo.service;

import jdanimal.demo.service.models.UserStoreUploadModel;
import jdanimal.demo.service.views.StoreViewModel;

import java.util.List;

public interface StoreService {
    void uploadStore(UserStoreUploadModel userStoreUploadModel, String username);
    List<StoreViewModel> getAllStores();
    List<StoreViewModel> getAllStoresByUser(String username);
    void removeStore(String id);
}
