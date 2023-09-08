package hr.ewallet.domain.account;

public class Account {
	private String accountNo;
	private String accountType;
	private String currency;
	private String glAccountNo;
	private String customerId;
	// And more information here
	
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getGlAccountNo() {
		return glAccountNo;
	}
	public void setGlAccountNo(String glAccountNo) {
		this.glAccountNo = glAccountNo;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
}
