package com.zombie.services;

import com.google.android.gcm.server.InvalidRequestException;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.zombie.models.User;
import com.zombie.utility.Globals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * Created by vivex on 12/6/15.
 */
@Service
@ComponentScan("com.zombie")
@EnableAutoConfiguration
public class NotificationService {
    public NotificationService(){}
    @Autowired
    UserService userService;
    /**
    * gcmRegId is the id which android app will give to server (one time)
    **/
    public boolean pushNotificationToGCM(String gcmRegId,String message, User user){
        System.out.println("GCM server key" + Globals.getGCMServerKey());
        System.out.println("breakpoints");
        final String GCM_API_KEY = Globals.getGCMServerKey();
        final int retries = 3;
        System.out.println("Push notification service entry");
        Sender sender = new Sender(GCM_API_KEY);
        Message msg = new Message.Builder().addData("message",message).build();

        try {
            if(user.getGcmRegId() != null) {
                System.out.println("foundUser with gcmRegId: " + user.getGcmRegId());

                Result result = sender.send(msg, gcmRegId, retries);
                /**
                * if you want to send to multiple then use below method
                * send(Message message, List<String> regIds, int retries)
                **/


                if (StringUtils.isEmpty(result.getErrorCodeName())) {
                    System.out.println("GCM Notification is sent successfully" + result.toString());
                    return true;
                }

                System.out.println("Error occurred while sending push notification :" + result.getErrorCodeName());

            }else {
                System.out.println("No token could be associated with this account!");
            }
        } catch (InvalidRequestException e) {
            System.out.println("Invalid Request");
        } catch (IOException e) {
            System.out.println("IO Exception");
        }
        return false;

    }
}