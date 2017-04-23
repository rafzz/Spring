package hello;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController extends Thread{
	
	public UserController() {
		this.start();
	}
	
	private static HashMap<User, Date> credentials = new HashMap<User, Date>();

	@GetMapping("/secretMethod"/*+"/{login}"*/)
	//@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public ResponseEntity secretMethod(@RequestParam(value="login") String login, 
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

	public static HashMap<User, Date> getCredentials() {
		return credentials;
	}

	public static void setCredentials(HashMap<User, Date> credentials) {
		UserController.credentials = credentials;
	}

	public String createUUID() {
		String result = "";
		Random random = new Random();

		for (int i = 0; i < 6; i++) {
			result += random.nextInt(9);
		}

		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(System.currentTimeMillis());

		result += "_" + timeStamp;

		return result;
	}

	@PostMapping
	public ResponseEntity login(@RequestBody User user) {

		for (Map.Entry<User, Date> entry : credentials.entrySet()) {

			if (entry.getKey().equals(user)) {
				entry.setValue(new Date());
				return ResponseEntity.status(HttpStatus.OK).body(user);
			}
		}

		user.setUuid(createUUID());
		credentials.put(user, new Date());
		return ResponseEntity.status(HttpStatus.CREATED).body(user);
	}

	@GetMapping
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
