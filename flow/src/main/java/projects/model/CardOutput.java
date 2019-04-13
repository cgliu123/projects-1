package projects.model;

public class CardOutput {
	private String uuid;
	private String token;
	
	public CardOutput() {
	}
	
	public CardOutput(String uuid, String token) {
		this.uuid = uuid;
		this.token = token;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
