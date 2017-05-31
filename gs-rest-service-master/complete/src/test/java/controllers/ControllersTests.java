/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package controllers;

import static org.assertj.core.api.Assertions.contentOf;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.isNull;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.*;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

import classes.Product;
import classes.RequestHolder;
import classes.User;

import com.fasterxml.jackson.databind.ObjectMapper;

import classes.User;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ControllersTests {

	private static final String MAIN_URL = "http://localhost:8080";
	private static final String LOGIN = "test1";
	private static final String PASSWORD = "test1"; 
	
	
	private static String UUID;
	private static boolean uuidFlag=true;
	
	
	private static final  Client client = ClientBuilder.newClient();

    
    @Test
    public void a_firstSignUp() throws Exception {
    	
    	
    	
    	Response response = client.target(MAIN_URL+"/user/signUp")
				.request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(new User(LOGIN, PASSWORD), 
						MediaType.APPLICATION_JSON_TYPE), Response.class);
    	
    	assertThat(response.getStatus(), Matchers.either(Matchers.is(Response.Status.CREATED.getStatusCode()))
    			.or(Matchers.is(Response.Status.FORBIDDEN.getStatusCode())));
    	
    	assertThat( response.readEntity(String.class), Matchers.either(Matchers.is("Succesfully registered!"))
    			.or(Matchers.is("Already registered!")));
    	
  
    	
    }
    
    
    @Test
    public void b_signIn() throws Exception {
    	
    	
    	Response response = client.target(MAIN_URL+"/user/signIn")
				.request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(new User(LOGIN, PASSWORD), 
						MediaType.APPLICATION_JSON_TYPE), Response.class);
    		
    		User user = response.readEntity(User.class);
    		
    		if(uuidFlag){
    			UUID =  user.getUuid();
    			uuidFlag=false;
    		}
    		
    		
    		

    	assertThat(response.getStatus(), Matchers.either(Matchers.is(Response.Status.OK.getStatusCode()))
    			.or(Matchers.is(Response.Status.FORBIDDEN.getStatusCode())));
    	assertThat( user, Matchers.either(Matchers.is(new User(LOGIN, PASSWORD)))
    			.or(Matchers.is(new User())));
    	
    	
  	
    }
    
    @Test
    public void c_getLoggedIn() throws Exception {
    	
    	
    	Response response = client.target(MAIN_URL+"/user/getUsersList")
				.request(MediaType.APPLICATION_JSON)
				.get(Response.class);
    	

    	assertEquals(response.getStatus(), Response.Status.OK.getStatusCode());
    	

  	
    }
    
    @Test
    public void d_add() throws Exception {

    	RequestHolder holder = new RequestHolder(new User(LOGIN, PASSWORD, UUID), new Product("Milk", 1.50));

		Response response = client.target(MAIN_URL+"/product/add")
				.request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(holder, MediaType.APPLICATION_JSON_TYPE), Response.class);
		
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus() );
		
		holder = new RequestHolder(new User("user", "user", "uuid"), new Product("Milk", 1.50));

		response = client.target(MAIN_URL+"/product/add")
				.request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(holder, MediaType.APPLICATION_JSON_TYPE), Response.class);
		
		assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), response.getStatus() );

    	
    	
    }
    
    @Test
    public void e_show() throws Exception {

		Response response = client.target(MAIN_URL+"/product/show")
				.request(MediaType.APPLICATION_JSON)
				.get(Response.class);
		
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus() );
			
    }
    
    @Test
    public void f_remove() throws Exception {

    	int id =1;
    	
    	Response response = client.target(MAIN_URL+"/product/remove?id="+id)
				.request(MediaType.APPLICATION_JSON)
				.put(Entity.entity(new User(LOGIN, PASSWORD, UUID), MediaType.APPLICATION_JSON_TYPE), Response.class);
		
    	assertThat(response.getStatus(), Matchers.either(Matchers.is(Response.Status.OK.getStatusCode()))
    			.or(Matchers.is(Response.Status.BAD_REQUEST.getStatusCode())));
    	
    	assertThat(response.readEntity(String.class), Matchers.either(Matchers.is("Removed product!"))
    			.or(Matchers.is("No such product!")));
    	
    	response = client.target(MAIN_URL+"/product/remove?id="+id)
				.request(MediaType.APPLICATION_JSON)
				.put(Entity.entity(new User("user", "user", "uuid"), MediaType.APPLICATION_JSON_TYPE), Response.class);
			
    	assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), response.getStatus() );
    	assertEquals("Unauthorized!", response.readEntity(String.class));
    }
    
    @Test
    public void g_signOut() throws Exception {
    	
    	
    	Response response = client.target(MAIN_URL+"/user/signOut")
				.request(MediaType.APPLICATION_JSON)
				.put(Entity.entity(new User(LOGIN, PASSWORD, UUID), 
						MediaType.APPLICATION_JSON_TYPE), Response.class);
    	

    	assertEquals(Response.Status.OK.getStatusCode(), response.getStatus() );
    	assertEquals("Succesfully logged out!", response.readEntity(String.class));
    	
    	response = client.target(MAIN_URL+"/user/signOut")
				.request(MediaType.APPLICATION_JSON)
				.put(Entity.entity(new User(LOGIN, PASSWORD, UUID), 
						MediaType.APPLICATION_JSON_TYPE), Response.class);
    	
    	assertEquals( Response.Status.UNAUTHORIZED.getStatusCode(),response.getStatus());
    	assertEquals("Unauthorized!", response.readEntity(String.class));
    	
    }
    
    
    

    
  
    

}