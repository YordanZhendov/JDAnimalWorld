package jdanimal.demo;

import jdanimal.demo.service.*;
import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableCaching
@EnableScheduling
@AllArgsConstructor
public class DemoApplication {

    private final UserService userService;
    private final AnimalService animalService;
    private final AccessoryService accessoryService;
    private final StoreService storeService;
    private final EncyclopediaService encyclopediaService;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);

    }

    @Scheduled(cron = "@weekly")
    public void printUsersCount(){
        this.userService.sendEmail();
    };

    @Scheduled(cron = "* * * * * ?")
    public void updateCaching(){
        this.userService.updateCash();
        this.animalService.updateAnimalCash();
        this.accessoryService.updateAccessoryCash();
        this.storeService.updateStoreCash();
        this.encyclopediaService.updateEncyclopedia();
    };
}
