package pe.edu.vallegrande.app.model;

public class Student {

	private Integer student_id;
	private String names;
	private String lastname;
	private String email;
	private String document_type;
	private String document_number;
	private String semester;
	private String career;
	private String active;

	public Student() {
		// TODO Auto-generated constructor stub
	}
	
	
	public Integer getStudent_id() {
		return student_id;
	}


	public void setStudent_id(Integer student_id) {
		this.student_id = student_id;
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


	public String getSemester() {
		return semester;
	}


	public void setSemester(String semester) {
		this.semester = semester;
	}


	public String getCareer() {
		return career;
	}


	public void setCareer(String career) {
		this.career = career;
	}


	public String getActive() {
		return active;
	}


	public void setActive(String active) {
		this.active = active;
	}


	@Override
	public String toString() {
		String data = "[student_id: " + this.student_id;
		data += ", names: " + this.names;
		data += ", lastname: " + this.lastname;
		data += ", email: " + this.email;
		data += ", document_type: " + this.document_type;
		data += ", document_number: " + this.document_number;
		data += ", semester: " + this.semester;
		data += ", career: " + this.career;
		data += ", active: " + this.active + "]";
		return data;
	}


}