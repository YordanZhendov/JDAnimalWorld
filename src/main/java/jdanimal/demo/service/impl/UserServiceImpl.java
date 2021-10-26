package jdanimal.demo.service.impl;

import jdanimal.demo.data.*;
import jdanimal.demo.data.Store;
import jdanimal.demo.service.models.UserRegisterUploadModel;
import jdanimal.demo.service.views.RoleServiceViewModel;
import jdanimal.demo.web.binding.UserLoginBinding;
import jdanimal.demo.repository.AccessoryRepository;
import jdanimal.demo.repository.AnimalRepository;
import jdanimal.demo.repository.StoreRepository;
import jdanimal.demo.repository.UserRepository;
import jdanimal.demo.service.RoleService;
import jdanimal.demo.service.UserService;
import jdanimal.demo.service.UserValidationSerivce;
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
    private final UserValidationSerivce userValidationSerivce;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ModelMapper modelMapper;
    private final RoleService roleService;
    private final ValidationUtil validationUtil;


    @Override
    public boolean register(UserRegistrationBinding userRegistrationBinding) {
        UserRegisterUploadModel userRegisterUploadModel = this.modelMapper.map(userRegistrationBinding, UserRegisterUploadModel.class);

        if(!validationUtil.isValid(userRegisterUploadModel)){
            return false;
        }

        User user=this.modelMapper.map(userRegisterUploadModel,User.class);

        if(!userValidationSerivce.checkUser(user)){
            return false;
        }

        user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));
        user.setConfirmPassword(this.bCryptPasswordEncoder.encode(user.getConfirmPassword()));

        if(userRepository.count() == 0){
            roleService.seedRoles();
            user.setAuthorities(this.roleService.findAllRoles()
                    .stream().filter(roleServiceViewModel -> !roleServiceViewModel.getAuthority().equals("SUSPENDED")).map(r->this.modelMapper
                            .map(r,Role.class))
                    .collect(Collectors.toSet()));
        }else {

            user.setAuthorities(new LinkedHashSet<>());
            user.getAuthorities().add(this.modelMapper.map(this.roleService.findByAuthority("GUEST"),Role.class));
        }
        this.userRepository.save(user);
        this.userRepository.findAll();
        return true;

    }

    @Override
    public User validUser(UserLoginBinding userLoginBinding) {
        userLoginBinding.setPassword(this.bCryptPasswordEncoder.encode(userLoginBinding.getPassword()));
        User userLogin = this.modelMapper.map(userLoginBinding, User.class);
        return this.userRepository.findByUsernameAndPassword(userLogin.getUsername(), userLogin.getPassword()).orElse(null);
    }

    @Override
    public UserProfileViewModel findByUsername(String currentUserName) {
        User byUsername = this.userRepository.findByUsername(currentUserName);
        return this.modelMapper.map(byUsername, UserProfileViewModel.class);
    }



    @Override
    public List<AnimalViewModel> getAllAnimalsByUser(String username) {
        return this.animalRepository.getAnimalByUser(username)
                .stream()
                .map(animal -> this.modelMapper.map(animal,AnimalViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<AccessoryViewModel> getAllAccessoriesByUser(String username) {
        return this.accessoryRepository.getAccessoriesByUser(username)
                .stream()
                .map(accessory -> this.modelMapper.map(accessory,AccessoryViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public void removeAnimalFromUsers(Animal animalById) {
        List<User> allUsers = userRepository.getAllUsers();
        for (User allUser : allUsers) {
            allUser.getLikedAnimals().remove(animalById);
        }
    }

    @Override
    public void removeAccessoryFromUsers(Accessory accessoryById) {
        List<User> allUsers = userRepository.getAllUsers();
        for (User allUser : allUsers) {
            allUser.getLikedAccessories().remove(accessoryById);
        }
    }

    @Override
    public void removeUser(String id) {
        List<User> allUsersInDB = getAllUsersInDB();
        List<Animal> animalByUser = animalRepository.getAnimalByUserId(id);
        for (Animal animal : animalByUser) {
            for (User user : allUsersInDB) {
                user.getLikedAnimals().remove(animal);
                userRepository.saveAndFlush(user);
            }
            animalRepository.deleteById(animal.getId());
        }
        List<Accessory> accessoriesByUser = accessoryRepository.getAccessoryByUserId(id);
        for (Accessory accessory : accessoriesByUser) {

            for (User user : allUsersInDB) {
                user.getLikedAccessories().remove(accessory);
                userRepository.saveAndFlush(user);
            }
            accessoryRepository.deleteById(accessory.getId());
        }
        List<Store> storeByUser = storeRepository.getStoreByUserId(id);
        for (Store store : storeByUser) {
            storeRepository.deleteById(store.getId());

        }


        this.userRepository.deleteById(id);
        this.userRepository.findAll();
    }


    @Override
    public void updateProfile(UserUpdateProfileBinding userUpdateProfileBinding) {
        User user = this.userRepository.findByUsername(userUpdateProfileBinding.getUsername());
        user.setEmail(userUpdateProfileBinding.getEmail());
        user.setPhoneNumber(userUpdateProfileBinding.getPhoneNumber());
        user.setFullName(userUpdateProfileBinding.getFullName());
        this.userRepository.save(user);
    }

    @Override
    public List<User> getAllUsersInDB() {
        return this.userRepository.getAllUsers();
    }

    @Override
    public List<UserProfileViewModel> getAllUsersForUserControl(){
        return this.userRepository.getAllUsersByRoleType().stream()
                .map(user -> this.modelMapper.map(user,UserProfileViewModel.class)).collect(Collectors.toList());
    }

    @Override
    public void saveUrl(String username,String fileName) {
        User byUsername = this.userRepository.findByUsername(username);
        byUsername.setUrlProfilePicture("https://jdanimalsworld.s3.eu-central-1.amazonaws.com/" + fileName);
        this.userRepository.saveAndFlush(byUsername);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(s);
    }

    @Override
    public void sendEmail(){

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

        List<User> all = this.userRepository.findAll();

        List<String> emails=new ArrayList<>();
        emails.add("jordan.zhendov@abv.bg");
        for (User user : all) {
            emails.add(user.getEmail());
        }

        if(emails.size() == 0){
            return;
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

        } catch (MessagingException mex) {
            mex.printStackTrace();
        }

    }

    @Override
    public void suspendUser(String id) {
        User allById = userRepository.findAllById(id);

        RoleServiceViewModel suspended = this.roleService.findByAuthority("SUSPENDED");

        Set<Role> authorities = allById.getAuthorities();
        authorities.removeAll(authorities);
        authorities.add(this.modelMapper.map(suspended, Role.class));

        allById.setAuthorities(authorities);
        this.userRepository.flush();
    }

    @Override
    public void activateUser(String id) {
        User allById = userRepository.findAllById(id);

        RoleServiceViewModel guest = this.roleService.findByAuthority("GUEST");

        Set<Role> authorities = allById.getAuthorities();
        authorities.removeAll(authorities);
        authorities.add(this.modelMapper.map(guest, Role.class));

        allById.setAuthorities(authorities);
        this.userRepository.flush();
    }

    @Override
    public String checkUserStatus(String currentUserName) {
        UserProfileViewModel byUsername = findByUsername(currentUserName);
        RoleServiceViewModel suspended = roleService.findByAuthority("SUSPENDED");

        Set<Role> authorities = byUsername.getAuthorities();

        for (Role authority : authorities) {
            if(authority.getId().equals(suspended.getId())){
                return "suspended";
            }
        }

        return "active";

    }


}
