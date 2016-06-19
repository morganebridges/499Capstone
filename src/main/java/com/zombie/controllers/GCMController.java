package com.zombie.controllers;

import com.zombie.models.User;
import com.zombie.repositories.UserRepository;
import com.zombie.services.NotificationService;
import com.zombie.services.UserService;
import com.zombie.utility.LatLng;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RestController;

import com.zombie.models.User;
import com.zombie.repositories.UserRepository;
import com.zombie.utility.LatLng;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/gcm")
@RestController
public class GCMController {
    @Autowired
    UserService userService;
    @Autowired
    NotificationService noteService;

    @RequestMapping(path="/register", method=RequestMethod.POST)
    public ResponseEntity<User>update(@RequestParam String gcmId, @RequestParam int key, HttpServletRequest request, HttpServletResponse response){

        User user = userService.findUserById(key);
        user.setGcmRegId(gcmId);
        userService.save(user);

        noteService.pushNotificationToGCM(gcmId, "Here is a message in response to your stuff that is geat");
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

}

