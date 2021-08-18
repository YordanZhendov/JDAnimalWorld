package jdanimal.demo.service;

import jdanimal.demo.service.models.UserAccessoryUploadModel;
import jdanimal.demo.service.views.AccessoryViewModel;
import jdanimal.demo.service.views.UserProfileViewModel;

import java.util.List;

public interface AccessoryService {
    void upload(UserAccessoryUploadModel userAccessoryUploadModel, UserProfileViewModel byUsername);
    List<AccessoryViewModel> getAllAccessories();
}
