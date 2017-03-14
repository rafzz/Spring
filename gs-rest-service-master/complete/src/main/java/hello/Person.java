package hello;

public class Person {

    private long id;
    
	private final String firstName;
    private final String lastName;

    public Person(long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    public Person(){
    	this.firstName = "firstName";
    	this.lastName = "lastName";
    }
    
    public void setId(long id) {
		this.id = id;
	}

	public long getId() {
        return id;
    }

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public boolean equalsId(Person person){
		
		if(!(this.id==person.id)){
			return false;
		}
		
		return true;
	}
	
	
	
	

    
    
}
