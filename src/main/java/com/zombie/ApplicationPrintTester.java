package com.zombie;

import com.zombie.controllers.DownloadExcelController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import com.zombie.models.User;
import com.zombie.models.Zombie;
import com.zombie.repositories.UserRepository;
import com.zombie.repositories.ZombieRepository;
@ComponentScan("com.zombie")
@EnableAutoConfiguration
public class ApplicationPrintTester {
	//@Autowired
	//private ZombieRepository zombieRepo;
	
	public void loadReferenceData(ZombieRepository zombieRepo, UserRepository userRepo){
		System.out.println("Load Reference Data");
	    /*
	     * Tests for zombie 	
	     */
		Zombie zombie = new Zombie("testTag");
		zombieRepo.save(zombie);
		
		User user = new User("testTag");
	    userRepo.save(user);
		System.out.println("Testing to see if user id is updated on save:");
	    System.out.println(user.getId());
	    System.out.println("Testing Zombie Repository Methods");
	    Zombie testZombie = zombieRepo.findByGamerTag("testTag");
	    System.out.println("Test Zombie from gamerTag search\ngamerTag: " + testZombie.getGamerTag()
	    		+ "\nId: " + testZombie.getId()); 
	    
	    long id = testZombie.getId();
	    System.out.println("Test zombie id from getter: " + id);
	    	testZombie = null;
	    	testZombie = zombieRepo.findById(id);
	    System.out.println("Test Zombie from id search\ngamerTag: " + testZombie.getGamerTag()
		+ "\nId: " + testZombie.getId());

		System.out.println("Testing logic for user key situation:");
		user = new User();
		userRepo.save(user);
		user.setClientKey(user.getId());
		System.out.println(user.getClientKey());
		DownloadExcelController downloadController = new DownloadExcelController();
		downloadController.downloadFile();

	}
}
