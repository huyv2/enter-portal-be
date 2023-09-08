package hr.ewallet.domain.card;

public class Card {
	private String cardNo;
	private String cardType;
	private String currency;
	private String bin;
	private String cardGlAccountService;
	private String issuer;
	private String customerId;
	
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getBin() {
		return bin;
	}
	public void setBin(String bin) {
		this.bin = bin;
	}
	public String getCardGlAccountService() {
		return cardGlAccountService;
	}
	public void setCardGlAccountService(String cardGlAccountService) {
		this.cardGlAccountService = cardGlAccountService;
	}
	public String getIssuer() {
		return issuer;
	}
	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
}
