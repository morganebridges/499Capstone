package com.zombie;


//locals
import com.zombie.models.Zombie;
import com.zombie.repositories.ZombieRepo;
import com.zombie.repositories.ZombieRepository;

//system
import java.util.Arrays;
import java.util.List;

//frameworks
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import com.fasterxml.jackson.core.JsonProcessingException;


@SpringBootApplication(scanBasePackages = {"com.zombie"})
@ComponentScan("com.zombie")
@EnableAutoConfiguration
public class Application implements CommandLineRunner{
    @Autowired
    ZombieRepository zombieRepo;
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);

        System.out.println("Let's inspect the beans provided by Spring Boot:");

        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }
       
    }
    @Override
    public void run(String ...args){
    		System.out.println("Inside run method");
    		loadReferenceData();
    		
    }
    public void loadReferenceData(){
    		System.out.println("Load Reference Data");
        Zombie zombie = new Zombie("testTag");
        zombieRepo.save(zombie);
    }


}