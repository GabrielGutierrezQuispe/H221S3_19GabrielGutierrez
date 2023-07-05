package pe.edu.vallegrande.app.model;

public class Student {

	private Integer student_id;
	private String documentNumber;
	private String documenType;
	private String names;
	private String lastname;
	private String email;
	private String semester;
	private String career;
	private String active;
	
	
	public Student() {
		// TODO Auto-generated constructor stub
	}


	public Student(Integer student_id, String documentNumber, String documenType, String names, String lastname,
			String email, String semester, String career, String active) {
		super();
		this.student_id = student_id;
		this.documentNumber = documentNumber;
		this.documenType = documenType;
		this.names = names;
		this.lastname = lastname;
		this.email = email;
		this.semester = semester;
		this.career = career;
		this.active = active;
	}


	public Integer getStudent_id() {
		return student_id;
	}


	public void setStudent_id(Integer student_id) {
		this.student_id = student_id;
	}


	public String getDocumentNumber() {
		return documentNumber;
	}


	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}


	public String getDocumenType() {
		return documenType;
	}


	public void setDocumenType(String documenType) {
		this.documenType = documenType;
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
		return "Student [student_id=" + student_id + ", documentNumber=" + documentNumber + ", documenType="
				+ documenType + ", names=" + names + ", lastname=" + lastname + ", email=" + email + ", semester="
				+ semester + ", career=" + career + ", active=" + active + "]";
	}
	

}