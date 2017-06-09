package po;

public class User {
	public String username;
	public String ip;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public User(String username, String ip) {
		super();
		this.username = username;
		this.ip = ip;
	}
	
	public User(){}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof User)){
			return false;
		}
		User user = (User)obj;
		return this.ip.equals(user.ip);
		
	}

	@Override
	public int hashCode() {
		return ip.hashCode();
	}
	
	
}
