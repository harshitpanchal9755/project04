package in.co.rays.proj4.bean;

import java.math.BigDecimal;
import java.util.Date;

public class GiftcardBean {
	private long id;
	private String code;
	private BigDecimal amount;
	private Date expiredate;
	private String status;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public String getCode() {
		return code;
		
	}
	
	public void setCode(String code) {
		this.code = code;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Date getExpiredate() {
		return expiredate;
	}

	public void setExpiredate(Date expiredate) {
		this.expiredate = expiredate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
