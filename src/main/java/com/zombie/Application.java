package com.zombie;


//locals

import com.zombie.repositories.UserRepository;
import com.zombie.repositories.ZombieRepository;
import com.zombie.services.NotificationService;
import com.zombie.services.UserService;
import com.zombie.services.ZombieService;
import com.zombie.services.scheduledTasks.ZombieGenerationScheduler;
import com.zombie.services.scheduledTasks.ZombieMovementScheduler;
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
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

//frameworks

@ComponentScan("com.zombie")
@EnableAutoConfiguration(exclude = { RepositoryRestMvcAutoConfiguration.class })
@SpringBootApplication(scanBasePackages = {"com.zombie.services", "com.zombie.repositories"})
@EnableScheduling
public class Application implements CommandLineRunner{
    @Autowired
    UserService userService;
    @Autowired
    ZombieService zombieService;
    @Autowired
    ZombieRepository zombieRepo;
    @Autowired
    UserRepository userRepo;
    @Autowired
    ZombieMovementScheduler zMoveScheduler;
    @Autowired
    ZombieGenerationScheduler zGenScheduler;
    @Autowired
    NotificationService noteService;

    @PersistenceContext
    private EntityManager entityManager;
    Logger log = LoggerFactory.getLogger(Application.class);

    @Autowired
    ApplicationActiveUsers guru;



    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);

        LoggerFactory.getLogger(Application.class).info("Zombie Application is online.");

    }
    @Override
    public void run(String ...args){
    		////log.trace("Inside @Override CommandLineRunner.run method");
    		ApplicationPrintTester printTester = new ApplicationPrintTester();

            while(guru == null){
                System.out.println("We're waiting for the guru");
            }
            TestDataPrep prep = new TestDataPrep(userService, guru);

            prep.populate(30);
    }

    /**
     * This Awkward method allows our beloved guru to rise to his throne, sitting finally aloft a seat of inscrutible
     * power.
     */

}
