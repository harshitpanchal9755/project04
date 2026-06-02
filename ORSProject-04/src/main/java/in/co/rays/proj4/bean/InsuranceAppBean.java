package in.co.rays.proj4.bean;

public class InsuranceAppBean extends BaseBean {

	private String customerName;
	private String policyType;
	private double Amount;
	private String claimStatus;

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getPolicyType() {
		return policyType;
	}

	public void setPolicyType(String policyType) {
		this.policyType = policyType;
	}

	public double getAmount() {
		return Amount;
	}

	public void setAmount(double amount) {
		Amount = amount;
	}

	public String getClaimStatus() {
		return claimStatus;
	}

	public void setClaimStatus(String claimStatus) {
		this.claimStatus = claimStatus;
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return policyType;
	}

}
