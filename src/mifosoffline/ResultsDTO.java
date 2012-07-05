package mifosoffline;

public class ResultsDTO {

	private int installmentId;
	private String globalAccNo,payDate;
	private double principle,principlePaid,interest,interestPaid,totalPaid;
	public int getInstallmentId() {
		return installmentId;
	}
	public void setInstallmentId(int installmentId) {
		this.installmentId = installmentId;
	}
	public String getGlobalAccNo() {
		return globalAccNo;
	}
	public void setGlobalAccNo(String globalAccNo) {
		this.globalAccNo = globalAccNo;
	}
	public String getPayDate() {
		return payDate;
	}
	public void setPayDate(String dueDate) {
		this.payDate = dueDate;
	}
	public double getPrinciple() {
		return principle;
	}
	public void setPrinciple(double principle) {
		this.principle = principle;
	}
        public double getTotalPaid() {
		return totalPaid;
	}
	public void setTotalPaid(double totalPaid) {
		this.totalPaid = totalPaid;
	}
        
	public double getPrinciplePaid() {
		return principlePaid;
	}
	public void setPrinciplePaid(double principlePaid) {
		this.principlePaid = principlePaid;
	}
	public double getInterest() {
		return interest;
	}
	public void setInterest(double interest) {
		this.interest = interest;
	}
	public double getInterestPaid() {
		return interestPaid;
	}
	public void setInterestPaid(double interestPaid) {
		this.interestPaid = interestPaid;
	}
	
	
	
}
