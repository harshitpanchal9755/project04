package in.co.rays.proj4.bean;

import in.co.rays.proj4.controller.BaseCtl;

public class BlockchainBean extends BaseBean {

	private String transcactionHash;
	private String senderAddress;
	private String receiverAddress;
	private double gasFee;

	public String getTranscactionHash() {
		return transcactionHash;
	}

	public void setTranscactionHash(String transcactionHash) {
		this.transcactionHash = transcactionHash;
	}

	public String getSenderAddress() {
		return senderAddress;
	}

	public void setSenderAddress(String senderAddress) {
		this.senderAddress = senderAddress;
	}

	public String getReceiverAddress() {
		return receiverAddress;
	}

	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}

	public double getGasfee() {
		return gasFee;
	}

	public void setGasfee(double gasfee) {
		this.gasFee = gasFee;
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return transcactionHash;
	}

	

	

}
