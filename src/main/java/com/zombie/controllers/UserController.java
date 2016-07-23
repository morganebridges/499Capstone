package com.zombie.controllers;

import com.zombie.models.User;
import com.zombie.models.Zombie;
import com.zombie.models.dto.ClientUpdateDTO;
import com.zombie.models.dto.UserActionDto;
import com.zombie.services.UserService;
import com.zombie.services.ZombieService;
import com.zombie.utility.Globals;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@ComponentScan("com.zombie")
@EnableAutoConfiguration
@RequestMapping("/user")
@RestController
public class UserController {
	@Autowired
	UserService userService;

	@Autowired
	ZombieService zombieService;

	private final Logger log = LoggerFactory.getLogger(UserController.class);


	@RequestMapping(path="/update", method=RequestMethod.POST)
	public ResponseEntity<ClientUpdateDTO>update(@RequestBody UserActionDto userActionDto, HttpServletRequest request, HttpServletResponse response) throws IllegalStateException{
		HashMap<Long, Zombie> zombieMap;

		System.out.println(userActionDto.toString());
		log.trace("User update endpoint hit userId={} actionId={} latitude={} longitude={} targetId={}",
				userActionDto.getId(), userActionDto.getAction(), userActionDto.getLatitude(),
				userActionDto.getLongitude(), userActionDto.getTargetId());

		User user = userService.findUserById(userActionDto.getId());
		if(user == null)
			throw new IllegalStateException("User does not exist in the system!");
		if(!Globals.zombiesGenerated && user.getLatitude() > 5){
			zombieService.requestZombies(user, 5);
			Globals.zombiesGenerated = true;
		}
		Globals.userLastUpdate = System.currentTimeMillis();

		//update location of user from the DTO
		if(userActionDto.getLatitude() != 0 && userActionDto.getLongitude() != 0)
			user.setLocation(userActionDto.getLatitude(), userActionDto.getLongitude());
		userService.save(user);

		System.out.println("user controller before attack");
		System.out.println(user.toString());
		//TODO: Refactor to maket he code more meaningful - item salvaging etc
		long targetId = userActionDto.getAction();
		Zombie attackedZombie = null;
		if(userActionDto.action == UserActionDto.Action.ATTACK){
			attackedZombie = userService.attackZombie(user, userActionDto.getTargetId());
			targetId = attackedZombie.getId();

		}

		//log.trace("generating test zombies size={}", zombieList.size());
		ArrayList<Zombie> list = zombieService.findZombiesInRange(user);
		list = zombieService.valZomList(list);
		ClientUpdateDTO dtoReturn = new ClientUpdateDTO(targetId, list, user, userActionDto.action);
		//log.trace("Returning zombies. Total zombie list length={}", zombieList.size());
		for(Zombie zom : list){
			System.out.println("Zombie : " + "id" + zom.getId() + "CLient : " + zom.getClientKey()
					+ "hp: " + zom.getHp());
		}
		return new ResponseEntity<ClientUpdateDTO>(dtoReturn, HttpStatus.OK);
	}

	@RequestMapping(path="/login", method=RequestMethod.POST)
	public ResponseEntity<User>login(@RequestBody long uid, HttpServletRequest request, HttpServletResponse response){
		//TODO: again, we shouldnt be creating new users in the login endpoint
		//log.trace("In user login endpoint userId={}", uid);
		User user = userService.findUserById(uid);
		if(user != null){
			userService.login(user);
			return new ResponseEntity<>(user, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);


	}


	@RequestMapping(path="/new", method=RequestMethod.POST)
	public ResponseEntity<User>createUser(@RequestBody String userName, HttpServletRequest request, HttpServletResponse response) throws IllegalStateException {
		//log.debug("RequestBody /new " + userName);
		User nullUser = null;
		if(userName == null || userName.length() < 1)
			return new ResponseEntity<>(nullUser, HttpStatus.BAD_REQUEST);

		User newUser = new User(userName);
		userService.save(newUser);
		userService.login(newUser);
		return new ResponseEntity<>(newUser, HttpStatus.OK);
	}


	@RequestMapping(path="/attack", method=RequestMethod.POST)
	public ResponseEntity<Zombie>attack(@RequestBody UserActionDto userActionDto, HttpServletRequest request, HttpServletResponse response) throws IllegalStateException {
		//TODO: What is the point of having the action field if we are hitting action specific endpoints?

		log.debug("In user attack endpoint userId={} actionId={} latitude={} longitude={} targetId={}",
				userActionDto.getId(), userActionDto.getAction(), userActionDto.getLatitude(),
				userActionDto.getLongitude(), userActionDto.getTargetId());

		ResponseEntity<Zombie> theReturn;
		Zombie attackedZombie = null;
		User user = userService.findUserById(userActionDto.getId());

		user.setLocation(userActionDto.getLatitude(), userActionDto.getLongitude());

		if(userActionDto.action == UserActionDto.Action.ATTACK){
			//log.debug("Attack action found userId={} zombieId={}", userActionDto.getId(), userActionDto.getTargetId());
			attackedZombie = userService.attackZombie(user, userActionDto.getTargetId());

		} else {
			log.error("Attack endpoint hit without attack action set userId={} actionId={}",
					userActionDto.getId(), userActionDto.getAction());
		}

		if(attackedZombie != null){
			//log.info("userId={} attacking zombieId={}", userActionDto.getId(), userActionDto.getTargetId());
			theReturn = new ResponseEntity<>(attackedZombie, HttpStatus.OK);
		}else{
			log.warn("The attacked zombieId={} from userId={} was null in the user controller. Was it already dead?",
					userActionDto.getTargetId(), userActionDto.getId());
			theReturn = new ResponseEntity<>(attackedZombie, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return theReturn;
	}
}

