package com.zombie;

import com.zombie.models.User;
import com.zombie.models.Zombie;
import com.zombie.repositories.UserRepository;
import com.zombie.repositories.ZombieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
@ComponentScan("com.zombie")
@EnableAutoConfiguration
public class ApplicationPrintTester {
	//@Autowired
	//private ZombieRepository zombieRepo;

	Logger log = LoggerFactory.getLogger(ApplicationPrintTester.class);
	
	public void loadReferenceData(ZombieRepository zombieRepo, UserRepository userRepo){
		//log.trace("in loadReferenceData");
	    /*
	     * Tests for zombie 	
	     */
		Zombie zombie = new Zombie(0, -95.2, 45);
		zombieRepo.save(zombie);
		
		User user = new User("testTag");
	    userRepo.save(user);
		//log.debug("Testing to see if user is updated on save. userId={}", user.getId());
	    Zombie testZombie = zombieRepo.findByClientKey(user.getId()).iterator().next();
		long id = testZombie.getId();
		//log.debug("Testing Zombie find by user. Found zombieId={}", id);

		//log.debug("Testing Zombie find by id. Found zombieId={}", zombieRepo.findById(id).getId());

		user = new User();
		userRepo.save(user);
		//log.debug("Testing logic for user key situation: userId={}", user.getId());

	}
}
