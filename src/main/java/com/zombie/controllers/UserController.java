package com.zombie.controllers;
import com.zombie.models.ResponseModels.UserResponse;
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

@ComponentScan("com.zombie")
@EnableAutoConfiguration
@RequestMapping("/user")
@RestController
public class UserController {
	@Autowired
	UserRepository userRepo;

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
        		return theReturn;
        }else{
        		System.out.println("User not found in database, creating new user");
        		user = new User(name);
        		userRepo.save(user);	
        		return new ResponseEntity<User>(user, HttpStatus.OK);
        	
        }
        
    }
	@RequestMapping(path="/update", method=RequestMethod.POST)
	public ResponseEntity<LatLng[]>update(@RequestBody LatLng latLng, HttpServletRequest request, HttpServletResponse response){
		LatLng pos1 = new LatLng(45.01, -93.25);
		LatLng pos2 = new LatLng(45.11, -93.35);
		LatLng pos3 = new LatLng(45.21, -93.15);
		
		LatLng[] posArr = {pos1, pos2, pos3};
		return new ResponseEntity<LatLng[]>(posArr, HttpStatus.OK);
	}
	@RequestMapping(path="/login", method=RequestMethod.POST)
	public ResponseEntity<UserResponse>login(@RequestBody long uid, HttpServletRequest request, HttpServletResponse response){
		ResponseEntity<UserResponse> theResponse = null;
		if(userService.findUserById(uid) != null){
			theResponse = new ResponseEntity<>(new UserResponse(userService.findUserById(uid)), HttpStatus.OK);
			return theResponse;
		} else{
			User user = new User();
			userService.save(user);
			user.setClientKey(user.getId());
			System.out.println(user.getClientKey());
			return new ResponseEntity<>(new UserResponse(user), HttpStatus.OK);
		}
	}

}

