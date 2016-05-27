package com.zombie.repositories;
import com.zombie.models.*;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by morganebridges on 5/25/16.
 */
@Repository
public interface ZombieRepository extends CrudRepository<Zombie, Long>{
    List<Zombie> findByGamerTag(String gamerTag);

    List<Zombie> findById(long id);
}
