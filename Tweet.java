package twitterPackage;

public class Tweet {
	private User user;
	private String message;
	
	public Tweet(User user, String message) {
		this.user= user;
		this.message = message;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		message = message;
	}

}
