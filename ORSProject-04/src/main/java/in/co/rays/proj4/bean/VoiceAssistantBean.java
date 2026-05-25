package in.co.rays.proj4.bean;

public class VoiceAssistantBean extends BaseBean {

	private String userVoice;
	private String response;
	private String language;
	private double accuracy;

	public String getUserVoice() {
		return userVoice;
	}

	public void setUserVoice(String userVoice) {
		this.userVoice = userVoice;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public double getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(double accuracy) {
		this.accuracy = accuracy;
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return response;
	}

}
