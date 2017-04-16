package hello;



import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class RestClient {

	public RestClient(){
		
	}
	
	public void test(){
		
		Client client = ClientBuilder.newClient();
		 	Response response = client
		 			.target("http://localhost:8080/greeting?token=e550d817-534c-4140-bb76-4395f83cac08")
		 			.request(MediaType.APPLICATION_JSON)
		 			.get(Response.class);
		
		System.out.println(response.readEntity(Person.class));
	}
}
