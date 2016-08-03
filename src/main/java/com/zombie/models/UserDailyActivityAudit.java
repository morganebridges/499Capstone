package com.zombie.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by morganebridges on 5/25/16.
 */
@Entity
public class UserDailyActivityAudit {
    @Id
    @GeneratedValue
    private long id;
	private Date day;
	private int numberOfActiveUsers;
	public UserDailyActivityAudit(){}
	public UserDailyActivityAudit(int numberOfActiveUsers, Date day) {
		this.numberOfActiveUsers = numberOfActiveUsers;
		this.day = day;
	}

	public long getId() {
		return id;
	}

	public int getNumberOfActiveUsers() {
		return numberOfActiveUsers;
	}

	public Date getDay() {
		return this.day;
	}

	@Override
	public boolean equals(Object otherObject){
		try{
			UserDailyActivityAudit otherAuditObject = (UserDailyActivityAudit)otherObject;
		}catch(ClassCastException e){
			return false;
		}

		return id == ((UserDailyActivityAudit)otherObject).getId();
	}

	@Override
	public String toString(){
		return "UserDailyActivityAudit id=" + this.id + " numberOfActiveUsers=" + this.numberOfActiveUsers + " day=" + day;
	}
}
