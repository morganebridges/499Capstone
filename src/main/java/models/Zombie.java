package models;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by morganebridges on 5/25/16.
 */
@Entity
public class Zombie {
    @Id
    @GeneratedValue
    private long id;
    private String gamerTag;

    protected Zombie(){}

    public Zombie(String gamerTag){
        this.gamerTag = gamerTag;
    }

    @Override
    public String toString(){
        return String.format(
          "GamerTag[id=%d, gamerTag='%s]",
                id, gamerTag
        );
    }


}
