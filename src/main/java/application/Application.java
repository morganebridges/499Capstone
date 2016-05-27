package application;

import java.util.Arrays;
import java.util.List;
import models.Zombie;
import repositories.ZombieRepository;

import org.springframework.context.ApplicationContext;


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


@SpringBootApplication
@EnableAutoConfiguration
public class Application {
    @Autowired
    private static ZombieRepository zombieRepo;
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);

        System.out.println("Let's inspect the beans provided by Spring Boot:");

        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }
        loadReferenceData();
    }
    public static void loadReferenceData(){
        Zombie zombie = new Zombie("testTag");
        zombieRepo.save(zombie);

    }


}