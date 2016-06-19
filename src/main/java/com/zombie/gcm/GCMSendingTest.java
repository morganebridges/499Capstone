
package com.zombie.gcm;
/*//
import com.google.android.gcm.server.InvalidRequestException;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import org.springframework.util.StringUtils;

import java.io.IOException;

public class GCMSendingTest {
    Message msg = new Message.Builder().build();
    Message msg2 = new Message.Builder().addData("message","Something New for You").build();

    try {

        //Result result = sender.send(msg2, account.getGcmRegId(), retries);

        if (StringUtils.isEmpty(result.getErrorCodeName())) {
            System.out.println(msg2.toString() + "GCM Notification is sent successfully" + result.toString());
            return true;
        }

        System.out.println("Error occurred while sending push notification :" + result.getErrorCodeName());

    } catch (InvalidRequestException e) {
        System.out.println("Invalid Request");
    } catch (IOException e) {
        System.out.println("IO Exception");
    }
}*/
