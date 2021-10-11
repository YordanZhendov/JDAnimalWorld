package jdanimal.demo.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import jdanimal.demo.service.AccessoryService;
import jdanimal.demo.service.AnimalService;
import jdanimal.demo.service.UserService;
import jdanimal.demo.service.views.UserProfileViewModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
@Slf4j
public class StorageServiceImpl {

    @Value("${application.bucket.name}")
    private String bucketName;

    @Autowired
    private AmazonS3 s3Client;

    private final UserService userService;
    private final AnimalService animalService;
    private final AccessoryService accessoryService;

    public StorageServiceImpl(UserService userService, AnimalService animalService, AccessoryService accessoryService) {
        this.userService = userService;
        this.animalService = animalService;
        this.accessoryService = accessoryService;
    }

    public String upload(MultipartFile file, UserProfileViewModel userProfileViewModel){
        File fileRecieved=convertMultiPartFileToFile(file);

        String fileName=userProfileViewModel.getUsername()+"_"+file.getOriginalFilename();
         s3Client.putObject(new PutObjectRequest(bucketName,fileName,fileRecieved));

        String replaceFileName = fileName.replace(" ", "+");
        userService.saveUrl(userProfileViewModel.getUsername(),replaceFileName);
         fileRecieved.delete();
         return "File"+fileName+"successfully uploaded";
    };


    public String uploadAnimalPicture(MultipartFile fileAnimal, UserProfileViewModel userProfileInfo, String id) {
        File fileRecieved=convertMultiPartFileToFile(fileAnimal);
        String fileName=userProfileInfo.getUsername()+"_"+fileAnimal.getOriginalFilename();
        s3Client.putObject(new PutObjectRequest(bucketName,fileName,fileRecieved));
        String replaceFileName = fileName.replace(" ", "+");

        animalService.saveUrlAnimal(id,replaceFileName);
        fileRecieved.delete();
         return "File"+fileName+"successfully uploaded";

    }

    public String uploadAccessoryPicture(MultipartFile fileAccessory, UserProfileViewModel userProfileInfo, String id) {
        File fileRecieved=convertMultiPartFileToFile(fileAccessory);
        String fileName=userProfileInfo.getUsername()+"_"+fileAccessory.getOriginalFilename();
        s3Client.putObject(new PutObjectRequest(bucketName,fileName,fileRecieved));
        String replaceFileName = fileName.replace(" ", "+");
        accessoryService.saveUrlAccessory(id,replaceFileName);
        fileRecieved.delete();
        return "File"+fileName+"successfully uploaded";
    }


//    public byte[] downloadFile(String fileName) {
//        S3Object s3Object = s3Client.getObject(bucketName, fileName);
//        S3ObjectInputStream inputStream = s3Object.getObjectContent();
//        try {
//            byte[] content = IOUtils.toByteArray(inputStream);
//            return content;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }


//    public String deleteFile(String fileName) {
//        s3Client.deleteObject(bucketName, fileName);
//        return fileName + " successfully removed";
//    }

    private File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            log.error("Error converting multipartFile to file", e);
        }
        return convertedFile;
    }

    public boolean checkFile(String originalFilename, Long size) {
        if(size == 0){
            return false;
        }

        String extPictureThree = originalFilename.substring(originalFilename.length()-4);
        String extPictureFour = originalFilename.substring(originalFilename.length()-5);
        return extPictureThree.equals(".jpg") || extPictureThree.equals(".png") || extPictureFour.equals(".jpeg");

    }



}

// Bucket Policy
//{
//    "Version": "2012-10-17",
//    "Statement": [
//        {
//            "Sid": "PublicReadGetObject",
//            "Effect": "Allow",
//            "Principal": "*",
//            "Action": "s3:GetObject",
//            "Resource": "arn:aws:s3:::jdanimalsworld/*"
//        }
//    ]
//}
