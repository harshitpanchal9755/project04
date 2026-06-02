package in.co.rays.proj4.bean;
import in.co.rays.proj4.bean.BaseBean;

public class DietPlanBean extends BaseBean {

	private String plantName;
	private int durationDay;
	private int price;

	public String getPlantName() {
		return plantName;
	}

	public void setPlantName(String plantName) {
		this.plantName = plantName;
	}

	public int getDurationDay() {
		return durationDay;
	}

	public void setDurationDay(int durationDay) {
		this.durationDay = durationDay;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return plantName;
	}

}
