package com.zombie.repositories;
import com.zombie.models.*;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.zombie.models.Zombie;
/**
 * A repository class for keeping instances of the Users
 * 
 * Interacts through calls to a MySql database with hibernate.
 * Created by morganebridges on 5/25/16.
 * 
 * This is privately owned intellectual property, respect dat bitches.
 */
@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
	
	@Query("select p from User p where p.id=:id")
	User findById(@Param("id") long id);
	
	@Query("select u from User u where UPPER(u.name) like UPPER(:name)")
	User findByName(@Param("name")String name);

}
