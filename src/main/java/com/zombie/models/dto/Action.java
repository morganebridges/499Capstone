package com.zombie.models.dto;

import javax.persistence.Entity;

/** An ENUM for the purposes of easy bookeeping in regards to the context of various data transfer events.
 * Created by morganebridges on 7/9/16.
 */
@Entity
public enum Action {
    NOTHING (0),
    ATTACK (1),
    SALVAGE (2);


    private final int code;
    Action(int code) {
        this.code = code;
    }

    private int getCode() {
        return this.code;
    }
}