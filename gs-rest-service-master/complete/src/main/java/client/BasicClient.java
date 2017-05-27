package client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import classes.Product;
import classes.RequestHolder;
import classes.User;
import controllers.ProductController;

public class BasicClient {

	private static Logger log = Logger.getLogger(BasicClient.class);
	
	private static final String LOGIN = "test";
	private static final String PASSWORD = "test"; 
	
	private static String UUID;
	
	private static Client client = ClientBuilder.newClient();

	public static String getLoggedUsers() {

		Response response = client.target("http://localhost:8080/user/getUsersList")
				.request(MediaType.APPLICATION_JSON)
				.get(Response.class);

		return response.readEntity(String.class);

	}

	public static String register() {

		Response response = client.target("http://localhost:8080/user/signUp")
				.request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(new User(LOGIN, PASSWORD), 
						MediaType.APPLICATION_JSON_TYPE), Response.class);

		return response.readEntity(String.class);

	}
	
	public static String login() {

		Response response = client.target("http://localhost:8080/user/signIn")
				.request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(new User(LOGIN, PASSWORD), 
						MediaType.APPLICATION_JSON_TYPE), Response.class);

		
		if(response.getStatus()==200){
			UUID =  response.readEntity(User.class).getUuid();
			return "SUcesfully logged in! ";
		}else if(response.getStatus()==403){
			return "Unable to log in!";
		}
		return "Server error!";

	}
	
	public static String loggOut() {

		Response response = client.target("http://localhost:8080/user/signOut")
				.request(MediaType.APPLICATION_JSON)
				.put(Entity.entity(new User(LOGIN, PASSWORD), 
						MediaType.APPLICATION_JSON_TYPE), Response.class);

		return response.readEntity(String.class);

	}
	
	
	public static String addProduct(Product product) {
		
		RequestHolder holder = new RequestHolder(new User(LOGIN, PASSWORD, UUID), product);

		Response response = client.target("http://localhost:8080/product/add")
				.request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(holder, MediaType.APPLICATION_JSON_TYPE), Response.class);

		return response.readEntity(String.class);

	}
	
	public static String showProducts() {
		
		

		Response response = client.target("http://localhost:8080/product/show")
				.request(MediaType.APPLICATION_JSON)
				.get(Response.class);

		return response.readEntity(String.class);

	}
	
	public static String removeProduct(int id) {
		
		
		Response response = client.target("http://localhost:8080/product/remove?id="+id)
				.request(MediaType.APPLICATION_JSON)
				.put(Entity.entity(new User(LOGIN, PASSWORD, UUID), MediaType.APPLICATION_JSON_TYPE), Response.class);

		return response.readEntity(String.class);

	}
	


	public static void main(String[] args) {

        
        
        log.info(register());
        log.info(getLoggedUsers()+"\n");
        
        log.info(login());
        log.info(getLoggedUsers()+"\n");
        
        log.info(addProduct(new Product("Milk", 1.50) ));
        log.info(addProduct(new Product("Butter", 1.70) ));
        
        log.info(showProducts());
        
        log.info(removeProduct(2));
        
        log.info(showProducts());
        
        
        log.info(loggOut());
        log.info(getLoggedUsers()+"\n");
        
        

        

	}

}
