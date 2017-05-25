package controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import classes.*;

@RestController
@RequestMapping("/user")
public class UserController extends Thread{
	
	public UserController() {
		this.start();
	}
	
	private static HashMap<User, Date> credentials = new HashMap<User, Date>();
	
	
	@Deprecated
	
	@GetMapping("/secretMethod")
	public ResponseEntity<String> secretMethod(@RequestParam(value="login") String login, 
									   @RequestParam(value="password") String password){

		for (Map.Entry<User, Date> entry : credentials.entrySet()) {
			
			if(entry.getKey().getLogin().equals(login) && 
				entry.getKey().getPassword().equals(password) && 
				entry.getValue()!=null){

				return  ResponseEntity.ok("TOP SECRET: luke skywalker is darth vader's son!");
				
			}
		}
		return  ResponseEntity.status(HttpStatus.FORBIDDEN).body("ACCESS DENIED!");
	}
	
	private String createUUID() {
		String result = "";
		Random random = new Random();

		for (int i = 0; i < 6; i++) {
			result += random.nextInt(9);
		}

		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(System.currentTimeMillis());

		result += "_" + timeStamp;

		return result;
	}
	
	private final String SIGN_UP_FORBIDDEN_MESAGE = "Already registered!";
	private final String SIGN_UP_CREATED_MESAGE = "Succesfully registered!";
	
	@PostMapping("/signUp")
	public ResponseEntity<String> signUp(@RequestBody User user) {
		
		//contains
		for( User storedUser : credentials.keySet()){
			if(storedUser.equals(user)){
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body(SIGN_UP_FORBIDDEN_MESAGE);
			}
		}
			

		user.setUuid(createUUID());
		credentials.put(user, null);
		return ResponseEntity.status(HttpStatus.CREATED).body(SIGN_UP_CREATED_MESAGE);
	}
	
	
	private final String SIGN_IN_OK_MESAGE = "Succesfully logged in!";
	private final String SIGN_IN_FORBIDDEN_MESAGE = "Unable to log in!";
	
	@PostMapping("/signIn")
	public ResponseEntity<String> signIn(@RequestBody User user) {
		
		
		for (Map.Entry<User, Date> entry : credentials.entrySet()) {

			if (entry.getKey().equals(user) && entry.getValue()==null) {
				entry.setValue(new Date());
				return ResponseEntity.status(HttpStatus.OK).body(SIGN_IN_OK_MESAGE);
			}
		}
		
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(SIGN_IN_FORBIDDEN_MESAGE);
		
	}
	
	private final String SIGN_OUT_OK_MESAGE = "Succesfully logged out!";
	private final String SIGN_OUT_FORBIDDEN_MESAGE = "No such user!";
	private final String SIGN_OUT_NOT_ALLOWED_MESAGE = "Already logged out!";
	
	@PutMapping("/signOut")
	public ResponseEntity<String> signOut(@RequestBody User user) {
		
		for(Entry<User, Date> entry : credentials.entrySet() ){
			
			if(entry.getKey().equals(user) && entry.getValue()==null){
				entry.setValue(null);
				return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(SIGN_OUT_NOT_ALLOWED_MESAGE);
				
			}else if(entry.getKey().equals(user)){
				entry.setValue(null);
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body(SIGN_OUT_OK_MESAGE);
			}
			
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(SIGN_OUT_FORBIDDEN_MESAGE);
	}
	
	
	@GetMapping("/getLoggedIn")
	public String getCredentialsMap() {
		String result = "";
		for (Map.Entry<User, Date> entry : credentials.entrySet()) {

			if (!(entry.getValue() == null)) {

				result += "\n" + entry.getKey().toString() + "  " + entry.getValue().toString();
			} else {
				result += "\n" + entry.getKey().toString() + "  " + entry.getValue();
			}

		}
		return result;
	}
	
	@Override
	public void run() {
		while (this.isAlive()) {

			for (Iterator<Entry<User, Date>> iterator = credentials.entrySet().iterator(); iterator.hasNext();) {

				Entry<User, Date> entry = iterator.next();

				if (entry.getValue() != null) {
					
					Date upTime = (Date) entry.getValue().clone();
					upTime.setMinutes(upTime.getMinutes() + 2);

					if (upTime.compareTo(new Date()) == 0 || (upTime).compareTo(new Date()) < 1) {

						entry.setValue(null);

					}
				}
			}
			try {
				sleep(700);

			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

	}
	
	
	

}
