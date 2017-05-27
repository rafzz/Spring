package classes;

public class RequestHolder {
	
	private  User user;
	private  Product product;

	
	
	
	public RequestHolder(User user, Product product) {
		super();
		this.user = user;
		this.product = product;
	}
	
	public RequestHolder() {

	}




	public  User getUser() {
		return this.user;
	}




	public  void setUser(User user) {
		this.user = user;
	}




	public  Product getProduct() {
		return this.product;
	}




	public  void setProduct(Product product) {
		this.product = product;
	}




	
	
	

}
