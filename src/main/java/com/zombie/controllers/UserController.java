package com.zombie.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.zombie.models.User;
import com.zombie.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class UserController {
	@Autowired
	UserRepository userRepo;
    @RequestMapping(path="/getuser", method=RequestMethod.POST)
   
    public ResponseEntity<User> getUser(@RequestParam(value="u") User user) {
        User theReturn = userRepo.findByGamerTag(user.getGamerTag());
        
        if(theReturn != null){
        	ResponseEntity<User> response = new ResponseEntity(theReturn, HttpStatus.OK);
        	return response;
        }else{
        	return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
        
    }

}

