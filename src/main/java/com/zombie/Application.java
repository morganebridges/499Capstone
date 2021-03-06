package com.zombie;


//locals

import com.zombie.repositories.UserRepository;
import com.zombie.repositories.ZombieRepository;
import com.zombie.services.UserService;
import com.zombie.utility.TestDataPrep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

//frameworks

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

    Logger log = LoggerFactory.getLogger(Application.class);

	private EntityManager entityManager;
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);

        LoggerFactory.getLogger(Application.class).info("Zombie Application is online.");

    }
    @Override
    public void run(String ...args){
    		log.trace("Inside @Override CommandLineRunner.run method");
    		ApplicationPrintTester printTester = new ApplicationPrintTester();
            TestDataPrep prep = new TestDataPrep(userService);
            prep.populate(100);
    }
}