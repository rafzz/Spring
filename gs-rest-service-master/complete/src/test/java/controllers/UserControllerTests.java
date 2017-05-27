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
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import classes.User;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    
    //@Test
    public void signUp() throws Exception {
    	
    	ObjectMapper mapper = new ObjectMapper();
    	
    	User user = new User("test","test");
    	
    	String jsonInString = mapper.writeValueAsString(user);

        //this.mockMvc.perform(get("/greeting")).andDo(print()).andExpect(status().isOk())
                //.andExpect(jsonPath("$.content").value("Hello, World!"));
        
    	
        this.mockMvc.perform(post("/signUp")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(jsonInString))
        .andExpect(status().isCreated());
        	
    }
    
    @Test
    public void showUsers() throws Exception {
    	
    
    	
        this.mockMvc.perform(get("/getUsersList")
        		.contentType(MediaType.APPLICATION_OCTET_STREAM_VALUE))
        .andExpect(status().isOk());
        		
        	
    }

    /*
    @Test
    public void paramGreetingShouldReturnTailoredMessage() throws Exception {

        this.mockMvc.perform(get("/greeting").param("name", "Spring Community"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("Hello, Spring Community!"));
    }
    */

}
