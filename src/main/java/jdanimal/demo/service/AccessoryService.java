package jdanimal.demo.service;

import jdanimal.demo.data.Accessory;
import jdanimal.demo.web.binding.UserAccessoryUploadBinding;
import jdanimal.demo.service.views.AccessoryViewModel;
import jdanimal.demo.service.views.UserProfileViewModel;

import java.util.List;

public interface AccessoryService {
    Accessory upload(UserAccessoryUploadBinding userAccessoryUploadBinding, UserProfileViewModel byUsername);
    boolean removeAccessoryById(String id);
    List<AccessoryViewModel> getAllAccessories();
    boolean saveUrlAccessory(String id, String replaceFileName);

    boolean addLikedAccessoryTotheCurrentUser(String id, String currentUserName);
    boolean disLikedAccessoryTotheCurrentUser(String id, String currentUserName);

    boolean updateAccessoryCash();
}
