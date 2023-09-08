package hr.ewallet.adapter.in.api.dto.response;

import com.google.gson.annotations.SerializedName;

public abstract class BaseResponseDto {
	@SerializedName("response_code")
	private String responseCode;
	@SerializedName("response_message")
	private String responseMessage;
	@SerializedName("response_signature")
	private String responseSignature;
	
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	public String getResponseMessage() {
		return responseMessage;
	}
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
}
