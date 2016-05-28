package com.zombie.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.zombie.models.User;
import com.zombie.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
public class UserController {
	@Autowired
	UserRepository userRepo;
    @RequestMapping(path="/getuser", method=RequestMethod.POST)
    public User getUser(String gamerTag) {
        return userRepo.findByGamerTag(gamerTag);
    }

}

