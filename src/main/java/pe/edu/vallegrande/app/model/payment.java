package pe.edu.vallegrande.app.model;

public class payment {
	
	private Integer id;
	private String typePayment;
	private String amount;
	private String dates;
	private String referenceNumber;
	private String termTime;
	private String states;
	
	public payment() {
	}

	public payment(Integer id, String typePayment, String amount, String dates,
			String referenceNumber, String termTime, String states) {
		super();
		this.id = id;
		this.typePayment = typePayment;
		this.amount = amount;
		this.dates = dates;
		this.referenceNumber = referenceNumber;
		this.termTime = termTime;
		this.states = states;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTypePayment() {
		return typePayment;
	}

	public void setTypePayment(String typePayment) {
		this.typePayment = typePayment;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getDates() {
		return dates;
	}

	public void setDates(String dates) {
		this.dates = dates;
	}

	public String getReferenceNumber() {
		return referenceNumber;
	}

	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	public String getTermTime() {
		return termTime;
	}

	public void setTermTime(String termTime) {
		this.termTime = termTime;
	}

	public String getStates() {
		return states;
	}

	public void setStates(String states) {
		this.states = states;
	}

	

	@Override
	public String toString() {
		String data = "[id: " + this.id;
		data += ", typePayment: " + this.typePayment;
		data += ", amount: " + this.amount;
		data += ", dates: " + this.dates;
		data += ", referenceNumber: " + this.referenceNumber;
		data += ", termTime: " + this.termTime;
		data += ", states: " + this.states + "]";
		return data;
	}

}
