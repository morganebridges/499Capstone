package com.zombie.models;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
/**
 * Created by morganebridges on 5/25/16.
 */
@Entity
public class User {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    
    //@Field int hp;

    protected User(){}

    public User(String name){
        this.name = name;
    }

 

	@Override
    public String toString(){
       return name;
    }

	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}
	public long getId() {
		// TODO Auto-generated method stub
		return id;
	}

}
