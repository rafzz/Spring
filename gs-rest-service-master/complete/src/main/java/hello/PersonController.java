package hello;


import static org.assertj.core.api.Assertions.entry;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class PersonController extends Thread{
	
	public PersonController(){
		this.start();
		
	}
	

    private static final String templateFirstName = "Hello, %s";
    private static final String templateLastName = "%s";
    private static final String token = "e550d817-534c-4140-bb76-4395f83cac08";
    
    private static ArrayList<Person> personList = new ArrayList<Person>();
    
    
    private final AtomicLong counter = new AtomicLong();
    
    
    
    private void tokenValidation(String givenToken){
    	if(!token.equals(givenToken)) throw new RuntimeException();
    	
    }
    
    private void personListIsEmpty(){
    	
    	if(personList.isEmpty()){
    		
    		throw new RuntimeException("personlist is empty!");
    	}
    }

    
    @GetMapping("/greeting")
    public Person greeting(
    		@RequestParam(value="token") String token,
    		@RequestParam(value="firstName", defaultValue="juzek") String firstName,
    		@RequestParam(value="lastName", defaultValue="kowal") String lastName) {
    	
    	tokenValidation(token);
    		
    	
    	Person tempPerson = new Person(counter.incrementAndGet(),
                String.format(templateFirstName, firstName), 
        		String.format(templateLastName, lastName));
    	
    	//personList.add(tempPerson);
    	
        return tempPerson;
    }
    
    
    
    // "Content-type: application/json" add to header !
    @PostMapping("/add")
    public Person add(@RequestBody Person person, @RequestParam(value="token") String token){
    	
    	tokenValidation(token);
    	
    	if(person!=null){
    		
    		person.setId(counter.incrementAndGet());
    		personList.add(person);
    	}
    	
    	return person;
    }
    
    
    @GetMapping("/getPersonList")
    public ArrayList<Person> getAll(@RequestParam(value="token") String token){
    	
    	tokenValidation(token);
    	
    	return personList;
    }
    
    
    @GetMapping("/getPerson/{personId}")
    public Person getPerson(@PathVariable("personId") int personId,
    						@RequestParam(value="token") String token){
    	
    	tokenValidation(token);
    	
    	for( Person person : personList ){
    		if(person.getId()==personId){
    			return person;
    		}
    	}
    	throw new RuntimeException("unknown id");
    }
    
    @DeleteMapping("/deletePerson")
    public ArrayList<Person> deleteNewPerson(@RequestParam(value="token") String token){
    	
    	tokenValidation(token);
    	personListIsEmpty();
    	
    	personList.remove(personList.size()-1);
    	
    	return personList; 
    }
    
    @DeleteMapping("/deletePerson/{personId}")
    public ArrayList<Person> deletePersonById(@PathVariable("personId") int personId,
			@RequestParam(value="token") String token){
    	
    	tokenValidation(token);
    	personListIsEmpty();
    	
    	for( Person person : personList ){
    		if(person.getId()==personId){
    			personList.remove(person);
    			return personList; 
    		}
    	}
    	
    	throw new RuntimeException("unknown id");
    }
    
    
    //-----------------------------------------------------------------------
    
    
    private static HashMap<User,Date> credentials = new HashMap<User,Date>();
    
    
    public static HashMap<User, Date> getCredentials() {
		return credentials;
	}

	public static void setCredentials(HashMap<User, Date> credentials) {
		PersonController.credentials = credentials;
	}

	public String createUUID(){
    	String result = "";
    	Random random = new Random();
    	
    	for(int i=0;i<6;i++){
    		result += random.nextInt(9);
    	}
    	
    	String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss")
    			.format(System.currentTimeMillis());
    	
    	result += "_"+timeStamp;


    	return result;
    }
    
    
    @PostMapping
    public User login(@RequestBody User user){
    	
    	user.setUuid(createUUID());
    	credentials.put(user, new Date());
    	
    	return user;
    }
    
    @GetMapping
    public String getCredentialsMap(){
    	String result="";
    	for(Map.Entry<User, Date> entry : credentials.entrySet()){
    		
    		result += "\n"+ entry.getKey().toString() +"  "+entry.getValue().toString();
    	}
    	return result;	
    }
    
    
	@Override
	public void run() {
		while(this.isAlive()){

			for(Iterator<Entry<User, Date>> iterator = credentials.entrySet().iterator(); iterator.hasNext(); ){
				
				Entry<User, Date> entry = iterator.next();
				
				Date upTime = (Date) entry.getValue().clone();
				upTime.setMinutes(upTime.getMinutes()+2);
				
				//System.out.println(new Date()+"------------"+upTime+"---compare "+(upTime).compareTo(new Date()));
				
				if(upTime.compareTo(new Date())==0 || (upTime).compareTo(new Date())<1){
					
					iterator.remove();
					
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
	
	
	
	
	
	
	
	
    
    
    
    

    
    
    
    
    
    
    
    

