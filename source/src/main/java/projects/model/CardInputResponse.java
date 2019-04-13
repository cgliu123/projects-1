package projects.model;

public class CardInputResponse {
	private String transactionId;
	private boolean success;
	
	public CardInputResponse(String transactionId) {
		this.transactionId = transactionId;
		this.success = true;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
}
