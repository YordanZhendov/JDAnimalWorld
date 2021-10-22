package jdanimal.demo.service;

import jdanimal.demo.web.binding.UserAccessoryUploadBinding;
import jdanimal.demo.service.views.AccessoryViewModel;
import jdanimal.demo.service.views.UserProfileViewModel;

import java.util.List;

public interface AccessoryService {
    void upload(UserAccessoryUploadBinding userAccessoryUploadBinding, UserProfileViewModel byUsername);
    List<AccessoryViewModel> getAllAccessories();
    void saveUrlAccessory(String id, String replaceFileName);
    void removeAccessory(String id);
    void addLikedAccessoryTotheCurrentUser(String id, String currentUserName);
    void disLikedAccessoryTotheCurrentUser(String id, String currentUserName);

}
