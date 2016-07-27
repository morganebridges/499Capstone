package com.zombie.repositories;

import com.zombie.models.UserActivityAudit;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * A repository class for keeping instances of the Users
 * 
 * Interacts through calls to a MySql database with hibernate.
 * Created by morganebridges on 5/25/16.
 * 
 * This is privately owned intellectual property, respect dat bitches.
 */
@Repository
public interface UserActivityAuditRepository extends PagingAndSortingRepository<UserActivityAudit, Long> {

}
