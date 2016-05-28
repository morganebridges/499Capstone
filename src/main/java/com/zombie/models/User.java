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
    private String gamerTag;
    
    //@Field int hp;

    protected User(){}

    public User(String gamerTag){
        this.gamerTag = gamerTag;
    }

 

	@Override
    public String toString(){
        return String.format(
          "GamerTag[id=%d, gamerTag='%s]",
                id, gamerTag
        );
    }

	public String getGamerTag() {
		// TODO Auto-generated method stub
		return gamerTag;
	}
	public long getId() {
		// TODO Auto-generated method stub
		return id;
	}

}
