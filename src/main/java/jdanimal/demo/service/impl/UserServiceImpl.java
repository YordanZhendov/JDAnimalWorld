package jdanimal.demo.service.impl;

import jdanimal.demo.data.*;
import jdanimal.demo.data.Store;
import jdanimal.demo.service.models.UserRegisterUploadModel;
import jdanimal.demo.repository.AccessoryRepository;
import jdanimal.demo.repository.AnimalRepository;
import jdanimal.demo.repository.StoreRepository;
import jdanimal.demo.repository.UserRepository;
import jdanimal.demo.service.RoleService;
import jdanimal.demo.service.UserService;
import jdanimal.demo.service.UserValidationService;
import jdanimal.demo.web.binding.UserRegistrationBinding;
import jdanimal.demo.web.binding.UserUpdateProfileBinding;
import jdanimal.demo.service.views.AccessoryViewModel;
import jdanimal.demo.service.views.AnimalViewModel;
import jdanimal.demo.util.ValidationUtil;
import jdanimal.demo.service.views.UserProfileViewModel;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.*;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AnimalRepository animalRepository;
    private final AccessoryRepository accessoryRepository;
    private final StoreRepository storeRepository;
    private final UserValidationService userValidationService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ModelMapper modelMapper;
    private final RoleService roleService;
    private final ValidationUtil validationUtil;


    @Override
    public boolean register(UserRegistrationBinding userRegistrationBinding) {
        UserRegisterUploadModel userRegisterUploadModel = this.modelMapper.map(userRegistrationBinding, UserRegisterUploadModel.class);

        if (!validationUtil.isValid(userRegisterUploadModel)) {
            return false;
        }

        User user = this.modelMapper.map(userRegisterUploadModel, User.class);

        if (!userValidationService.checkUser(user)) {
            return false;
        }

        user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));
        user.setConfirmPassword(this.bCryptPasswordEncoder.encode(user.getConfirmPassword()));

        if (userRepository.count() == 0) {
            roleService.seedRoles();
            user.setAuthorities(this.roleService.findAllRoles()
                    .stream().map(r -> this.modelMapper
                            .map(r, Role.class))
                    .collect(Collectors.toSet()));
        } else {

            user.setAuthorities(new LinkedHashSet<>());
            user.getAuthorities().add(this.modelMapper.map(this.roleService.findByAuthority("GUEST"), Role.class));
        }
        user.setUserStatus(true);
        this.userRepository.save(user);
        updateCash();
        return true;

    }

    @Override
    public UserProfileViewModel findByUsername(String currentUserName) {
        User byUsername = this.userRepository.findByUsername(currentUserName);
        return this.modelMapper.map(byUsername, UserProfileViewModel.class);
    }


    @Override
    public List<AnimalViewModel> getAllAnimalsByUserName(String username) {
        return this.animalRepository.getAnimalByUserUserName(username)
                .stream()
                .map(animal -> this.modelMapper.map(animal, AnimalViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<AccessoryViewModel> getAllAccessoriesByUserName(String username) {
        return this.accessoryRepository.getAccessoriesByUserUsername(username)
                .stream()
                .map(accessory -> this.modelMapper.map(accessory, AccessoryViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public boolean removeLikedAnimalFromUsers(Animal animalById) {
        try {
            List<User> allUsers = userRepository.getAllUsers();
            for (User allUser : allUsers) {
                allUser.getLikedAnimals().remove(animalById);
            }
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean removeLikedAccessoryFromUsers(Accessory accessoryById) {
        try {
            List<User> allUsers = userRepository.getAllUsers();
            for (User allUser : allUsers) {
                allUser.getLikedAccessories().remove(accessoryById);
            }
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean removeUser(String id) {
        try {
            List<Animal> animalByUser = animalRepository.getAnimalByUserId(id);
            List<Accessory> accessories = accessoryRepository.getAccessoryByUserId(id);
            List<Store> stores=storeRepository.getStoreByUserId(id);
            List<Accessory> allAccessories = accessoryRepository.findAll();
            List<Animal> alAnimals1 = animalRepository.findAll();

            User byId = userRepository.findById(id).orElse(null);
            for (Accessory accessory : allAccessories) {
                accessory.getUsers().remove(byId);
            }
            for (Animal animal : alAnimals1) {
                animal.getUsers().remove(byId);
            }
            for (Animal animal : animalByUser) {
                animal.getUsers().clear();
            }

            for (Animal animal : animalByUser) {
                animalRepository.delete(animal);
            }

            for (Accessory accessory : accessories) {
                accessory.getUsers().clear();
            }

            for (Accessory accessory : accessories) {
                accessoryRepository.delete(accessory);
            }

            for (Store store : stores) {
             this.storeRepository.delete(store);
            }

            if(byId != null){
                this.userRepository.delete(byId);

            }
            return true;
        }catch (Exception e){
            return  false;
        }
    }

    @Override
    public boolean updateProfile(UserUpdateProfileBinding userUpdateProfileBinding) {
        User user = this.modelMapper.map(userUpdateProfileBinding, User.class);

        if(userValidationService.checkDetails(user)){
            return false;
        }

        User userUpdate = this.userRepository.findByUsername(user.getUsername());
        userUpdate.setEmail(user.getEmail());
        userUpdate.setPhoneNumber(user.getPhoneNumber());
        userUpdate.setFullName(user.getFullName());
        this.userRepository.saveAndFlush(userUpdate);
        return true;
    }

    @Override
    public List<UserProfileViewModel> getAllUsersForUserControl(){
        return this.userRepository.getAllUsersWithoutAdmin().stream()
                .map(user -> this.modelMapper.map(user, UserProfileViewModel.class)).collect(Collectors.toList());
    }

    @Override
    public boolean saveUrl(String username,String fileName) {
        try {
            User byUsername = this.userRepository.findByUsername(username);
            byUsername.setUrlProfilePicture("https://jdanimalsworld.s3.eu-central-1.amazonaws.com/" + fileName);
            this.userRepository.saveAndFlush(byUsername);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(s);
    }

    @Override
    public boolean sendEmail(){

        // Sender's email ID needs to be mentioned
        String sender = "jzanimalteam@gmail.com";

        // Assuming you are sending email from through gmails smtp
        String host = "smtp.gmail.com";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication("jzanimalteam@gmail.com","JZanimalTeam94");

            }

        });

        List<User> all = this.userRepository.getAllUsersInDB();

        List<String> emails=new ArrayList<>();
        emails.add("jordan.zhendov@abv.bg");
        for (User user : all) {
            emails.add(user.getEmail());
        }

        if(emails.size() == 0){
            return false;
        }


//        // Recipient's email ID needs to be mentioned.
//        String receiver = "jordan.zhendov@abv.bg";



        // Used to debug SMTP issues
        session.setDebug(false);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(sender));

            // Set To: header field of the header.
            for (String email : emails) {

                message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            }


            // Set Subject: header field
            message.setSubject("JZ Team Information!");

            // Now set the actual message
            message.setText("Dear User, \nwe are so happy to have you in the family!" +
                    " \nPlease check the newest product and friends in our updated web application https://jd-animals-world.herokuapp.com ." +
                    "\n Do not hesitate to contact us." +
                    "\n Best regards," +
                    "\n JZ Animal Team");

//            System.out.println("sending...");
            // Send message
            Transport.send(message);
//            System.out.println("Sent message successfully....");
            return true;

        } catch (MessagingException mex) {
            mex.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean suspendUser(String id) {
        User deactivateUser = userRepository.findById(id).orElse(null);
        if(deactivateUser != null){
            deactivateUser.setUserStatus(false);
            this.userRepository.saveAndFlush(deactivateUser);
            updateCash();
            return true;
        }

        return false;
    }

    @Override
    public boolean activateUser(String id) {
        User activateUser = userRepository.findById(id).orElse(null);
        if(activateUser != null){
            activateUser.setUserStatus(true);
            this.userRepository.saveAndFlush(activateUser);
            updateCash();
            return true;
        }
        return false;
    }

    @Override
    public boolean updateCash() {
        try {
            this.userRepository.findAll();
            return true;
        }catch (Exception e){
            return false;
        }
    }


}
