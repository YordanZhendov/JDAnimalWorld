package jdanimal.demo;

import jdanimal.demo.service.UserService;
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

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);

    }

    @Scheduled(cron = "1 * * * * ?")
    public void printUsersCount(){
        this.userService.sendEmail();
    };
}
