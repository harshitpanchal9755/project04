package in.co.rays.proj4.bean;

public class LockerBean {
	private Long id;
	private String lockerNumber;
	private String lockerType;
	private Double annualFee;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLockerNumber() {
		return lockerNumber;
	}
	public void setLockerNumber(String lockerNumber) {
		this.lockerNumber = lockerNumber;
	}
	public String getLockerType() {
		return lockerType;
	}
	public void setLockerType(String lockerType) {
		this.lockerType = lockerType;
	}
	public Double getAnnualFee() {
		return annualFee;
	}
	public void setAnnualFee(Double annualFee) {
		this.annualFee = annualFee;
	}
	
	
}
