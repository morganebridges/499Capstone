package com.zombie.controllers;

import com.zombie.models.User;
import com.zombie.models.Zombie;
import com.zombie.models.dto.UserActionDto;
import com.zombie.repositories.UserRepository;
import com.zombie.repositories.ZombieRepository;
import com.zombie.services.UserService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;

@ComponentScan("com.zombie")
@EnableAutoConfiguration
@RequestMapping("/user")
@RestController
public class UserController {
	@Autowired
	UserRepository userRepo;

	@Autowired
	ZombieRepository zombieRepo;

	@Autowired
	UserService userService;

	private final Logger log = LoggerFactory.getLogger(UserController.class);

    
	@RequestMapping(path="/getuser", method=RequestMethod.POST)
    public ResponseEntity<User> getUser(@RequestParam(required=true) String name, HttpServletRequest request, HttpServletResponse response) {

		//TODO: I don't like that this endpoint is used for both getting a user and creating a new one.  What if thinks they are creating a new user but instead gets a current one?
		//TODO: Why aren't we getting users by id?
		log.debug("User controller hit, requesting user Object for name={}", name);
    		
        User user = userService.findUserByName(name);
        if(user != null) {
	        log.debug("User query to database produced user object with name={} id={}", user.getName(), user.getId());
        }else{
            user = new User(name);
	        userService.save(user);
	        log.info("Requested User not found in database, created new user with name={} userid={}", name, user.getId());
        }

		if(user.getLastUsedSerum() == null) {
			user.setLastUsedSerum(new Date());
			userService.save(user);
			log.warn("Encountered a userId={} with no lastusedSerum property, setting to lastUsedSerum={}",
					user.getId(), user.getLastUsedSerum());
		}
		return new ResponseEntity<>(user, HttpStatus.OK);
    }

	@RequestMapping(path="/update", method=RequestMethod.POST)
	public ResponseEntity<ArrayList<Zombie>>update(@RequestBody UserActionDto userActionDto, HttpServletRequest request, HttpServletResponse response) throws IllegalStateException{
		log.trace("User update endpoint hit userId={} actionId={} latitude={} longitude={} targetId={}",
				userActionDto.getId(), userActionDto.getAction(), userActionDto.getLatitude(),
				userActionDto.getLongitude(), userActionDto.getTargetId());
		User user = userService.findUserById(userActionDto.getId());
		if(user == null)
			throw new IllegalStateException("User does not exist in the system!");

		//update location of user from the DTO
		user.setLocation(userActionDto.getLatitude(), userActionDto.getLongitude());
		userService.save(user);
		//TODO: fix up the way we are generating / getting zombies for the backend - right now only provide 4 test zombies.
		//Iterable<Zombie> list = userService.update(user.getId());
		//Iterator<Zombie> it = list.iterator();

		//System.out.println("Zombies generated");
		//System.out.println(list);
		//generate some test zombies so we can ensure we always get some
		ArrayList<Zombie> testZoms = userService.generateTestZombies(user, 4);
		log.trace("generating test zombies size={}", testZoms.size());
		ArrayList<Zombie> zombList= new ArrayList<>();



		/*while(it.hasNext())
			zombList.add(it.next());*/

		zombList.addAll(testZoms);
		log.trace("Returning zombies. Total zombie list length={}", zombList.size());
		return new ResponseEntity<>(zombList, HttpStatus.OK);
	}

	@RequestMapping(path="/login", method=RequestMethod.POST)
	public ResponseEntity<User>login(@RequestBody long uid, HttpServletRequest request, HttpServletResponse response){
		//TODO: again, we shouldnt be creating new users in the login endpoint
		log.trace("In user login endpoint userId={}", uid);
		User user = userService.findUserById(uid);
		if(user != null){
			if(user.getName() == null) {
				String defaultName = "Generic Jerk";
				log.warn("userId={} found without a name.  Setting name to name={}", user.getId(), defaultName);
				user.setName(defaultName);
				userService.save(user);
			}
			userService.login(user);
			return new ResponseEntity<>(user, HttpStatus.OK);

		} else{
			user = new User();
			userService.save(user);
			if(user.getName() == null) {
				String defaultName = "Generic Jell";
				log.warn("New userId={} found without a name.  Setting default to name={}", user.getId(), defaultName);
				user.setName(defaultName);
				userService.save(user);
			}

			userService.login(user);

			log.info("New user being sent: userId={}", user.getId());

			log.trace("test"); //TODO: What purpose does this line serve?  Could it be more descriptive?
			return new ResponseEntity<>(user, HttpStatus.OK);
		}
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
			log.debug("Attack action found userId={} zombieId={}", userActionDto.getId(), userActionDto.getTargetId());
			attackedZombie = userService.attackZombie(user, userActionDto.getTargetId());
		} else {
			log.error("Attack endpoint hit without attack action set userId={} actionId={}",
					userActionDto.getId(), userActionDto.getAction());
		}

		if(attackedZombie != null){
			log.info("userId={} attacking zombieId={}", userActionDto.getId(), userActionDto.getTargetId());
			theReturn = new ResponseEntity<>(attackedZombie, HttpStatus.OK);
		}else{
			log.warn("The attacked zombieId={} from userId={} was null in the user controller. Was it already dead?",
					userActionDto.getTargetId(), userActionDto.getId());
			theReturn = new ResponseEntity<>(attackedZombie, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return theReturn;
	}
}

