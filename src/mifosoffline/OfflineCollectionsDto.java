package mifosoffline;

import java.io.Serializable;

public class OfflineCollectionsDto implements Serializable{
	
	private String loanGlobalNum;
	private String loanAmount;
	private String savingGlobalNum;
	private String savingAmount;
	private String savingTrxnDate;
	private String savingPaymentId;
	private int receiptId;
	
	
	
	public int getReceiptId() {
		return receiptId;
	}
	public void setReceiptId(int receiptId) {
		this.receiptId = receiptId;
	}
	public String getLoanGlobalNum() {
		return loanGlobalNum;
	}
	public void setLoanGlobalNum(String loanGlobalNum) {
		this.loanGlobalNum = loanGlobalNum;
	}
	public String getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}
	public String getSavingGlobalNum() {
		return savingGlobalNum;
	}
	public void setSavingGlobalNum(String savingGlobalNum) {
		this.savingGlobalNum = savingGlobalNum;
	}
	public String getSavingAmount() {
		return savingAmount;
	}
	public void setSavingAmount(String savingAmount) {
		this.savingAmount = savingAmount;
	}
	public String getSavingTrxnDate() {
		return savingTrxnDate;
	}
	public void setSavingTrxnDate(String savingTrxnDate) {
		this.savingTrxnDate = savingTrxnDate;
	}
	public String getSavingPaymentId() {
		return savingPaymentId;
	}
	public void setSavingPaymentId(String savingPaymentId) {
		this.savingPaymentId = savingPaymentId;
	}
	
	

}
