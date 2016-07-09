package com.zombie.ZCollections;

import com.zombie.models.User;

import java.util.ArrayList;
import java.util.List;

/** An object adapter that will help us to better manage our user lists.
 *
 * Created by morganebridges on 7/9/16.
 */
public class UserList {
    private List<User> list;

    /**
     * The default constructor will be of actual type ArrayList, although this is not necessary or particularly
     * relevant as we want this class to essentially process based on list interface
     */
    public UserList(){
        list = new ArrayList<User>();
    }
    public UserList(List<User> list){

        this.list = list;
    }
}
