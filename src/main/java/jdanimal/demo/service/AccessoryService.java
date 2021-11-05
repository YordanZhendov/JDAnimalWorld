package jdanimal.demo.service;

import jdanimal.demo.data.Accessory;
import jdanimal.demo.web.binding.UserAccessoryUploadBinding;
import jdanimal.demo.service.views.AccessoryViewModel;
import jdanimal.demo.service.views.UserProfileViewModel;

import java.util.List;

public interface AccessoryService {
    List<AccessoryViewModel> getAllAccessories();
    boolean updateAccessoryCash();

    Accessory upload(UserAccessoryUploadBinding userAccessoryUploadBinding, UserProfileViewModel byUsername);
    boolean removeAccessoryById(String id);
    boolean saveUrlAccessory(String id, String replaceFileName);

    boolean addLikedAccessoryToTheCurrentUser(String id, String currentUserName);
    boolean disLikedAccessoryToTheCurrentUser(String id, String currentUserName);

}
