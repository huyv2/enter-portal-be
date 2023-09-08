package hr.ewallet.application.port.in.command.request.momo.money;

public class TopupRequestCommand {
	private String token;
	private Long amount;
	private String type;
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
