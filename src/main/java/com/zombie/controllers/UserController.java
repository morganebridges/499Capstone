package com.zombie.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.zombie.models.User;
import com.zombie.repositories.UserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
   
    public ResponseEntity<User> getUser(@RequestParam(value="tag") String gamerTag, HttpServletRequest request, HttpServletResponse response) {
        User user = userRepo.findByGamerTag(gamerTag);
        ResponseEntity<User> theReturn = null;
        if(user != null){
        		theReturn = new ResponseEntity(user, HttpStatus.OK);
        	return theReturn;
        }else{
        	return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        	
        }
        
    }

}

