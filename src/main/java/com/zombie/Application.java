package com.zombie;


//locals
import com.zombie.models.Zombie;
import com.zombie.repositories.UserRepository;
import com.zombie.repositories.ZombieRepository;

//system
import java.util.Arrays;
import java.util.List;

//frameworks
import com.zombie.services.NotificationService;
import com.zombie.services.UserService;
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
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import com.fasterxml.jackson.core.JsonProcessingException;


@ComponentScan("com.zombie")
@EnableAutoConfiguration(exclude = { RepositoryRestMvcAutoConfiguration.class })
@SpringBootApplication(scanBasePackages = {"com.zombie.services", "com.zombie.repositories"})
public class Application implements CommandLineRunner{
    @Autowired
    UserService userService;
    @Autowired
    ZombieRepository zombieRepo;
    @Autowired
    UserRepository userRepo;
    @PersistenceContext
	private EntityManager entityManager;
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);

        System.out.println("Zombie Application is online.");
       
       
    }
    @Override
    public void run(String ...args){
    		System.out.println("Inside @Override CommandLineRunner.run method");
    		ApplicationPrintTester printTester = new ApplicationPrintTester();
    		printTester.loadReferenceData(zombieRepo, userRepo);
    		
    }

}