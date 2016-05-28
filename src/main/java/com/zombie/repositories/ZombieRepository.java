package com.zombie.repositories;
import com.zombie.models.*;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.zombie.models.Zombie;

/**
 * A repository class for keeping instances of the Zombie
 * 
 * Interacts through calls to a MySql database with hibernate.
 * Created by morganebridges on 5/25/16.
 * 
 * This is privately owned intellectual property, respect dat bitches.
 */


@Repository
public interface ZombieRepository extends PagingAndSortingRepository<Zombie, Long> {
	
	@Query("select p from Zombie p where p.id=:id")
	Zombie findById(@Param("id") long id);
	
	@Query("select u from Zombie u where UPPER(u.gamerTag) like UPPER(:gamerTag)")
	Zombie findByGamerTag(@Param("gamerTag")String gamerTag);

}
