package client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.*;

import classes.Product;
import classes.RequestHolder;
import classes.User;


public class BasicClient {

	private static Logger log = Logger.getLogger(BasicClient.class);
	
	private static final String MAIN_URL = "http://localhost:8080";
	private static final String LOGIN = "test";
	private static final String PASSWORD = "test"; 
	private static String UUID;

	private static final  Client client = ClientBuilder.newClient();

	public static String getUsersList() {

		Response response = client.target(MAIN_URL+"/user/getUsersList")
				.request(MediaType.APPLICATION_JSON)
				.get(Response.class);

		return response.readEntity(String.class);

	}

	public static String register() {

		Response response = client.target(MAIN_URL+"/user/signUp")
				.request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(new User(LOGIN, PASSWORD), 
						MediaType.APPLICATION_JSON_TYPE), Response.class);

		return response.readEntity(String.class);

	}
	
	private static final String OK_LOGIN_MESSAGE = "Sucesfully logged in!";
	private static final String FORBIDDEN_LOGIN_MESSAGE = "Unable to log in!";
	private static final String OTHER_LOGIN_MESSAGE = "Error!";
	public static String login() {

		Response response = client.target(MAIN_URL+"/user/signIn")
				.request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(new User(LOGIN, PASSWORD), 
						MediaType.APPLICATION_JSON_TYPE), Response.class);

		if(response.getStatus()==200){
			UUID =  response.readEntity(User.class).getUuid();
			return OK_LOGIN_MESSAGE;
		}else if(response.getStatus()==403){
			return FORBIDDEN_LOGIN_MESSAGE;
		}
		return OTHER_LOGIN_MESSAGE;

	}
	
	public static String loggOut() {

		Response response = client.target(MAIN_URL+"/user/signOut")
				.request(MediaType.APPLICATION_JSON)
				.put(Entity.entity(new User(LOGIN, PASSWORD, UUID), 
						MediaType.APPLICATION_JSON_TYPE), Response.class);

		return response.readEntity(String.class);
	}
	
	
	public static String addProduct(Product product) {
		
		RequestHolder holder = new RequestHolder(new User(LOGIN, PASSWORD, UUID), product);

		Response response = client.target(MAIN_URL+"/product/add")
				.request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(holder, MediaType.APPLICATION_JSON_TYPE), Response.class);

		return response.readEntity(String.class);

	}
	
	public static String showProducts() {
		
		

		Response response = client.target(MAIN_URL+"/product/show")
				.request(MediaType.APPLICATION_JSON)
				.get(Response.class);

		return response.readEntity(String.class);

	}
	
	public static String removeProduct(int id) {
		
		
		Response response = client.target(MAIN_URL+"/product/remove?id="+id)
				.request(MediaType.APPLICATION_JSON)
				.put(Entity.entity(new User(LOGIN, PASSWORD, UUID), MediaType.APPLICATION_JSON_TYPE), Response.class);

		return response.readEntity(String.class);

	}
	


	public static void main(String[] args) {

        
        
        log.info(register());
        log.info(getUsersList()+"\n");
        
        log.info(login());
        log.info(getUsersList()+"\n");
        
        log.info(addProduct(new Product("Milk", 1.50) ));
        log.info(addProduct(new Product("Butter", 1.70) ));
        
        log.info(showProducts());
        
        log.info(removeProduct(2));
        
        log.info(showProducts());
        
        
        log.info(loggOut());
        log.info(getUsersList()+"\n");
        
        

        

	}

}
