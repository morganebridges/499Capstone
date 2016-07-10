package com.zombie.services;

import com.google.android.gcm.server.InvalidRequestException;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.zombie.models.User;
import com.zombie.utility.Globals;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    //Collapse keys for identifying the type of notification. Max 4 for android.
    private static String mapUpdateKey = "mapUpdateKey";
    public NotificationService(){}
    @Autowired
    UserService userService;

    Logger log = LoggerFactory.getLogger(NotificationService.class);

    /**
    * gcmRegId is the id which android app will give to server (one time)
    **/
    public boolean pushNotificationToGCM(String gcmRegId,String message, User user){
        log.trace("In pushNotificationToGCM. gcmRegId={} message={} userId={} gcmServerKey={}",
                gcmRegId, message, user, Globals.GCMServerKey);
        final String GCM_API_KEY = Globals.GCMServerKey;
        final int retries = 3;

        Sender sender = new Sender(GCM_API_KEY);
        Message msg = new Message.Builder()
                .addData("message",message)
                .collapseKey(mapUpdateKey)
                .build();

        try {
            if(gcmRegId != null) {
                //log.trace("In try block of gcm service");
                Result result = sender.send(msg, gcmRegId, retries);
                /**
                * if you want to send to multiple then use below method
                * send(Message message, List<String> regIds, int retries)
                **/


                if (StringUtils.isEmpty(result.getErrorCodeName())) {
                    //log.info("GCM Notification is sent successfully to userId={} result={}", user, result.toString());
                    return true;
                }

                //log.error("Error occurred while sending push notification errorCode={}:", result.getErrorCodeName());

            }else {
                //log.warn("No token could be associated with this account! userId={}", user);
            }
        } catch (InvalidRequestException e) {
            //log.error("Invalid Request", e);
        } catch (IOException e) {
            //log.error("IO Exception", e);
        }
        return false;
    }
}