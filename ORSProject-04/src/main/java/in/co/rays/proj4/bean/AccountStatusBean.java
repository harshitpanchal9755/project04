package in.co.rays.proj4.bean;

public class AccountStatusBean extends BaseBean {

	private int checkAccountStatus;
	private String activeAccount;
	private String deactivateAccount;
	private String SuspendAccount;

	public int getCheckAccountStatus() {
		return checkAccountStatus;
	}

	public void setCheckAccountStatus(int checkAccountStatus) {
		this.checkAccountStatus = checkAccountStatus;
	}

	public String getActiveAccount() {
		return activeAccount;
	}

	public void setActiveAccount(String activeAccount) {
		this.activeAccount = activeAccount;
	}

	public String getDeactivateAccount() {
		return deactivateAccount;
	}

	public void setDeactivateAccount(String deactivateAccount) {
		this.deactivateAccount = deactivateAccount;
	}

	public String getSuspendAccount() {
		return SuspendAccount;
	}

	public void setSuspendAccount(String suspendAccount) {
		SuspendAccount = suspendAccount;
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return activeAccount;
	}

}
