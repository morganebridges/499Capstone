package com.zombie.controllers;
import com.zombie.models.Zombie;
import com.zombie.repositories.ZombieRepository;
import com.zombie.services.UserService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RestController;

import com.zombie.models.User;
import com.zombie.repositories.UserRepository;
import com.zombie.utility.LatLng;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@ComponentScan("com.zombie")
@EnableAutoConfiguration
@RequestMapping("/user")
@RestController
public class UserController {
	@Autowired
	UserRepository userRepo;

	@Autowired
	ZombieRepository zombieRepo;

	@Autowired
	UserService userService;



    
	@RequestMapping(path="/getuser", method=RequestMethod.POST)
    public ResponseEntity<User> getUser(@RequestParam(required=true) String name, HttpServletRequest request, HttpServletResponse response) {
        System.out.println("User controller hit, requesting user Object for name: " + name);
    		
        User user = userRepo.findByName(name);
        if(user != null)
        		System.out.println("User query to database produce user object with name: " + user.getName() + "\n with id: " + user.getId());
        ResponseEntity<User> theReturn = null;


        if(user != null){
        		theReturn = new ResponseEntity<User>(user, HttpStatus.OK);
        }else{
        		System.out.println("User not found in database, creating new user");
        		user = new User(name);
        		userRepo.save(user);	

        	
        }
		if(user.getLastUsedSerum() == null)
			user.setLastUsedSerum(new Date());
		userService.save(user);
		return new ResponseEntity<User>(user, HttpStatus.OK);
    }
	@RequestMapping(path="/update", method=RequestMethod.POST)
	public ResponseEntity<ArrayList<Zombie>>update(@RequestBody User user, HttpServletRequest request, HttpServletResponse response){
		userService.save(user);
		Iterable<Zombie> list = userService.update(user.getClientKey());
		Iterator<Zombie> it = list.iterator();

		ArrayList<Zombie> zombList= new ArrayList<Zombie>();
		while(it.hasNext())
			zombList.add(it.next());


		return new ResponseEntity<ArrayList<Zombie>>(zombList, HttpStatus.OK);
	}
	@RequestMapping(path="/login", method=RequestMethod.POST)
	public ResponseEntity<User>login(@RequestBody long uid, HttpServletRequest request, HttpServletResponse response){
		ResponseEntity<User> theResponse = null;
		User user = userService.findUserById(uid);
		if(user != null){
			userService.save(user);
			userService.login(user);
			theResponse = new ResponseEntity<User>(user, HttpStatus.OK);
			return theResponse;

		} else{
			user = new User();
			userService.save(user);
			user.setClientKey(user.getId());
			userService.save(user);

			userService.login(user);

			System.out.println("User being sent:");
			System.out.println("ID: " + user.getId());
			System.out.println("Client Key:" + user.getClientKey());

			System.out.println("test");
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}
	}

}

