package hello;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class PersonController {

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

    
    
    
    
    
    
    
    
}
