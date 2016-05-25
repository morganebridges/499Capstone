package main.java.repositories;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by morganebridges on 5/25/16.
 */
public interface ZombieRepository extends CrudRepository<Zombie, Long>{
    List<Zombie> findByGamerTag(String gamerTag);

    List<Zombie> findById(long id);
}
