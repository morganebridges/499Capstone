package com.zombie.models.ResponseModels;

import com.zombie.models.User;
import com.zombie.utility.LatLng;

/**
 * Created by morganebridges on 6/20/16.
 */
public class UserResponse {
    long id;
    long uuid;
    LatLng location;
    String gcmId;

    public UserResponse(User user){
        this.id = user.getId();
        this.uuid = user.getId();
        this.gcmId = user.getGcmRegId();
        this.location = user.getLocation();
    }
}
