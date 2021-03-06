package controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import classes.RequestHolder;
import classes.Product;
import classes.User;

@RestController
@RequestMapping("/product")
public class ProductController {
	
	private static final AtomicLong counter = new AtomicLong();
	private static ArrayList<Product> productsList = new ArrayList<Product>(){
		
		@Override
		public String toString() {
			
			String result="";
			for( Product product : productsList){
				result+="\n"+product.toString();
			}
			return result;
		}
		
	};
	
	
	public static ArrayList<Product> getProductsList() {
		return productsList;
	}

	private boolean isAuthorized(User user){
		
		
		for(Map.Entry<User, Date> entry  : UserController.getCredentials().entrySet()){
			
			if(entry.getKey().isLogged(user) && entry.getValue()!=null){
				return true;
			}
		}
		
		return false;
		
	}
	
	private final String OK_ADD_MESSAGE = "Added product!";
	private final String UNAUTHORIZED_ADD_MESSAGE = "Unauthorized!";
	
	@PostMapping("/add")
    public ResponseEntity<String> add(@RequestBody RequestHolder holder) {
		
		if(isAuthorized(holder.getUser())){
			
			Product product = holder.getProduct();
			product.setId( (int) counter.incrementAndGet()); 
			productsList.add(product);
			
			return ResponseEntity.status(HttpStatus.OK).body(OK_ADD_MESSAGE);
		}
		
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(UNAUTHORIZED_ADD_MESSAGE);
		
        
    }
	
	
	
	@GetMapping("/show")
    public ResponseEntity<String> show() {
			
			return ResponseEntity.status(HttpStatus.OK).body(productsList.toString());

    }
	
	
	private final String OK_REMOVE_MESSAGE = "Removed product!";
	private final String BAD_REQUEST_PRODUCT_MESSAGE = "No such product!";
	private final String UNAUTHORIZED_REMOVE_MESSAGE = "Unauthorized!";
	
	@PutMapping("/remove")
    public ResponseEntity<String> remove(@RequestBody User user, @RequestParam(value="id", required=true) int id) {
		
		if(isAuthorized(user)){

			for(Product product : productsList){
				
				if(product.getId()==id){
					productsList.remove(product);
					return ResponseEntity.status(HttpStatus.OK).body(OK_REMOVE_MESSAGE);
					
				}
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BAD_REQUEST_PRODUCT_MESSAGE);

		}else{
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(UNAUTHORIZED_REMOVE_MESSAGE);
		}
		
		
		
        
    }

}
