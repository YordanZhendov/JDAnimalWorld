package jdanimal.demo;

import jdanimal.demo.data.Animal;
import jdanimal.demo.data.User;
import org.checkerframework.checker.units.qual.A;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @RequestMapping(value = "/reg", method = RequestMethod.GET)
    public User firstPage() {

        User emp = new User();
        emp.setUsername("Jordan");
        emp.setPassword("22");
        emp.setConfirmPassword("22");
        emp.setEmail("jordan@abv.bg");
        emp.setPostcode("4445");
        emp.setPhoneNumber("089665885");

        return emp;
    }
    @RequestMapping(value = "/animalupload", method = RequestMethod.GET)
    public Animal animalUpload() {

        Animal emp = new Animal();
        emp.setAgeOfAnimal("3");
        emp.setFoodOfAnimal("Meat");
        emp.setKilogramsOfAnimal("15");
        emp.setTypeOfAnimal("Golden Retriver");
        emp.setNameOfAnimal("Rayan");

        return emp;
    }

}