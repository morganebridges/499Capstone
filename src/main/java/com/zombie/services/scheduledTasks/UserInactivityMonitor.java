package com.zombie.services.scheduledTasks;

import com.zombie.ApplicationActiveUsers;
import com.zombie.models.UserActivityAudit;
import com.zombie.repositories.UserActivityAuditRepository;
import com.zombie.utility.Globals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO: describe<br/>
 * <br/>
 * <em>Created on 7/26/16</em>
 *
 * @since 7.0
 */

@Component
@Scope("singleton")
public class UserInactivityMonitor {
	@Autowired
	ApplicationActiveUsers guru;
	@Autowired
	UserActivityAuditRepository userActivityAuditRepository;
	@Autowired
	UserDailyActivityMonitor userDailyActivityMonitor;

	private Map<Long, UserActivityTimingDTO> activeUsers = new HashMap<>();

	@Scheduled(fixedRate = Globals.USER_INACTIVITY_LOOP_TIME)
	public synchronized void monitorUsers() {
		long now = System.currentTimeMillis();
		List<Long> toBeRemoved = new ArrayList<>();
		activeUsers.keySet().stream().forEach(
				userId -> {
					UserActivityTimingDTO timing = activeUsers.get(userId);
					if (now - timing.lastActivity < Globals.USER_INACTIVITY_GRACE_TIME) {
						guru.deactivateUser(userId);
						UserActivityAudit auditRow = new UserActivityAudit(userId, now - timing.becameActive);
						userActivityAuditRepository.save(auditRow);
						toBeRemoved.add(userId);
					}
				}
		);

		toBeRemoved.stream().forEach(
				userId -> {
					activeUsers.remove(userId);
				}
		);
	}

	public synchronized void registerActivity(long userId) {
		long now = System.currentTimeMillis();
		if (activeUsers.containsKey(userId)) {
			activeUsers.get(userId).lastActivity = now;
		} else {
			UserActivityTimingDTO timing = new UserActivityTimingDTO(now, now);
			activeUsers.put(userId, timing);
			userDailyActivityMonitor.registerActivity(userId);
		}

	}

	private class UserActivityTimingDTO {
		private long becameActive;
		private long lastActivity;
		private UserActivityTimingDTO(long becameActive, long lastActivity) {
			this.becameActive = becameActive;
			this.lastActivity = lastActivity;
		}
	}
}
