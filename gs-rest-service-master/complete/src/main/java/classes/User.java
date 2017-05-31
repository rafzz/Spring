package classes;

public class User {

	private String login;
	private String password;
	private String uuid;
	

	public User(String login, String password) {
		this.login = login;
		this.password = password;

	}
	
	public User(String login, String password, String uuid) {
		this.login = login;
		this.password = password;
		this.uuid = uuid;
		
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public User() {
		this.login = "";
		this.password = "";
		this.uuid = "";
		
	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [login=" + login +"]";
	}

	@Override
	public boolean equals(Object obj) {

		User user = (User) obj;

		
		return this.login.equals(user.login) && this.password.equals(user.password);
	}

	public boolean isLogged(Object obj) {

		User user = (User) obj;
		return this.login.equals(user.login) && this.uuid.equals(user.uuid) && this.password.equals(user.password);
	}

}
