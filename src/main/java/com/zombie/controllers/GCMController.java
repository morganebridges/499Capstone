package com.zombie.controllers;

import com.zombie.models.User;
import com.zombie.repositories.UserRepository;
import com.zombie.services.NotificationService;
import com.zombie.services.UserService;
import com.zombie.utility.LatLng;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
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
@ComponentScan("com.fourninenine.zombie.services")
@RequestMapping("/gcm")
@RestController
public class GCMController {
    @Autowired
    UserService userService;
    @Autowired
    NotificationService noteService;

    @RequestMapping(path="/register", method=RequestMethod.POST)
    public ResponseEntity<User>register(@RequestParam String gcmId, @RequestParam long key, HttpServletRequest request, HttpServletResponse response){
        System.out.println("Hit GCM register endpoint");
        System.out.println("User provided token for gcm registration : " + gcmId);
        System.out.println("User provided key (user.id): "  + key);
        User user = userService.findUserById(key);
        if(user != null){
            user.setGcmRegId(gcmId);
            userService.save(user);

            System.out.println("user specs:");
            System.out.println("clientKey: " + user.getId());
            System.out.println("gcmId:" + gcmId);

            user.setGcmRegId(gcmId);
            userService.save(user);
            System.out.println("Calling gcm service");
            noteService.pushNotificationToGCM(gcmId, "Here is a message in response to your stuff that is great", user);
            return new ResponseEntity<User>(user, HttpStatus.OK);
        }else{
            if(key == -1)
                System.out.println("Key was not found correctly on client device");
            else System.out.println("Something strange is happening to be sure");
        }
        //Shitty return object wont let me return null!?
        User nullUser = null;
        return new ResponseEntity<User>(nullUser, HttpStatus.BAD_REQUEST);
    }

}

