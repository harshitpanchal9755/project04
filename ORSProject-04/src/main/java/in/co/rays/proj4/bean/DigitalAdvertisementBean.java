package in.co.rays.proj4.bean;

public class DigitalAdvertisementBean extends BaseBean {

	private String adTitle;
	private String targetAudience;
	private String impressions;
	private String clickRate;

	public String getAdTitle() {
		return adTitle;
	}

	public void setAdTitle(String adTitle) {
		this.adTitle = adTitle;
	}

	public String getTargetAudience() {
		return targetAudience;
	}

	public void setTargetAudience(String targetAudience) {
		this.targetAudience = targetAudience;
	}

	public String getImpressions() {
		return impressions;
	}

	public void setImpressions(String impressions) {
		this.impressions = impressions;
	}

	public String getClickRate() {
		return clickRate;
	}

	public void setClickRate(String clickRate) {
		this.clickRate = clickRate;
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return targetAudience;
	}

}
