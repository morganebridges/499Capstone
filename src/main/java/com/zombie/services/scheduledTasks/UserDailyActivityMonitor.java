package com.zombie.services.scheduledTasks;

import com.zombie.models.UserDailyActivityAudit;
import com.zombie.repositories.UserDailyActivityAuditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * TODO: describe<br/>
 * <br/>
 * <em>Created on 7/26/16</em>
 *
 * @since 7.0
 */

@Component
@Scope("singleton")
public class UserDailyActivityMonitor {
	public final String DAILY_CRON = "0 0 0 * * *";

	@Autowired
	UserDailyActivityAuditRepository userDailyActivityAuditRepository;

	private Set<Long> activeUsers = new HashSet<>();

	@Scheduled(cron = DAILY_CRON)
	public synchronized void monitorUsers() {
		UserDailyActivityAudit auditRow = new UserDailyActivityAudit(activeUsers.size());
		userDailyActivityAuditRepository.save(auditRow);
		activeUsers = new HashSet<>();
	}

	public synchronized void registerActivity(long userId) {
		activeUsers.add(userId);
	}
}
