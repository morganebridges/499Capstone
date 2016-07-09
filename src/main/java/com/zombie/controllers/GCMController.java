package com.zombie.controllers;

import com.zombie.models.User;
import com.zombie.services.NotificationService;
import com.zombie.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@ComponentScan("com.fourninenine.zombie.services")
@RequestMapping("/gcm")
@RestController
public class GCMController {
    @Autowired
    UserService userService;
    @Autowired
    NotificationService noteService;

    private final Logger log = LoggerFactory.getLogger(GCMController.class);

    @RequestMapping(path="/register", method=RequestMethod.POST)
    public ResponseEntity<User>register(@RequestParam String gcmId, @RequestParam long key, HttpServletRequest request, HttpServletResponse response){
        //log.trace("In GCMController.register() endpoint gcmToken={} userId={}", gcmId, key);
        User user = userService.findUserById(key);
        if(user != null){
            user.setGcmRegId(gcmId);
            userService.save(user);

            //log.info("Registering gcm userId={} cgmId={}", user.getId(), gcmId);

            user.setGcmRegId(gcmId);
            userService.save(user);
            //log.debug("Calling gcm service");
            noteService.pushNotificationToGCM(gcmId, "Here is a message in response to your stuff that is great", user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }else{
            if(key == -1)
                //log.warn("Key was not found correctly on client device");
            else //log.error("Key={} sent but no corresponding user found in the database.", key);

            return new ResponseEntity<>(user, HttpStatus.BAD_REQUEST);
        }
    }

}

