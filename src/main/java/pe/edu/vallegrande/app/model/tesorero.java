package pe.edu.vallegrande.app.model;

public class tesorero {

	private Integer administrative_id;
	private String names;
	private String lastname;
	private String email;
	private String document_type;
	private String document_number;
	private String passwords;
	private String active;

	public tesorero() {
		// TODO Auto-generated constructor stub
	}

	public tesorero(Integer administrative_id, String names, String lastname, String email, String document_type,
			String document_number, String passwords, String active) {
		super();
		this.administrative_id = administrative_id;
		this.names = names;
		this.lastname = lastname;
		this.email = email;
		this.document_type = document_type;
		this.document_number = document_number;
		this.passwords = passwords;
		this.active = active;
	}




	public Integer getAdministrative_id() {
		return administrative_id;
	}

	public void setAdministrative_id(Integer administrative_id) {
		this.administrative_id = administrative_id;
	}

	public String getNames() {
		return names;
	}

	public void setNames(String names) {
		this.names = names;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDocument_type() {
		return document_type;
	}

	public void setDocument_type(String document_type) {
		this.document_type = document_type;
	}

	public String getDocument_number() {
		return document_number;
	}

	public void setDocument_number(String document_number) {
		this.document_number = document_number;
	}

	public String getPasswords() {
		return passwords;
	}

	public void setPasswords(String passwords) {
		this.passwords = passwords;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	@Override
	public String toString() {
		String data = "[administrative_id: " + this.administrative_id;
		data += ", names: " + this.names;
		data += ", lastname: " + this.lastname;
		data += ", email: " + this.email;
		data += ", document_type: " + this.document_type;
		data += ", document_number: " + this.document_number;
		data += ", passwords: " + this.passwords;
		data += ", active: " + this.active + "]";
		return data;
	}


}