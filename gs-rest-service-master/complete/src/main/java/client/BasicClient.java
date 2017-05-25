package client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.*;

import classes.User;

public class BasicClient {

	private static Logger log = Logger.getLogger(BasicClient.class);
	
	private static final String LOGIN = "test";
	private static final String PASSWORD = "test"; 
	
	private static Client client = ClientBuilder.newClient();

	public static String getLoggedUsers() {

		Response response = client.target("http://localhost:8080/user/getLoggedIn")
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

		return response.readEntity(String.class);

	}
	
	public static String loggOut() {

		Response response = client.target("http://localhost:8080/user/signOut")
				.request(MediaType.APPLICATION_JSON)
				.put(Entity.entity(new User(LOGIN, PASSWORD), 
						MediaType.APPLICATION_JSON_TYPE), Response.class);

		return response.readEntity(String.class);

	}


	public static void main(String[] args) {

        
        
        log.info(register());
        log.info(getLoggedUsers()+"\n");
        
        log.info(login());
        log.info(getLoggedUsers()+"\n");
        
        log.info(loggOut());
        log.info(getLoggedUsers()+"\n");


	}

}
