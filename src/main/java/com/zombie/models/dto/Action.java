package com.zombie.models.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/** An ENUM for the purposes of easy bookeeping in regards to the context of various data transfer events.
 * Created by morganebridges on 7/9/16.
 */
@Entity
public enum Action {

    NOTHING (0),
    ATTACK (1),
    SALVAGE (2);

    @Id
    @GeneratedValue
    private long id;

    private final int code;
    Action(int code) {
        this.code = code;
    }

    private int getCode() {
        return this.code;
    }
}