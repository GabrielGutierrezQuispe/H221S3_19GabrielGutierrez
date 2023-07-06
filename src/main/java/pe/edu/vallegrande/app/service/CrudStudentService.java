package pe.edu.vallegrande.app.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pe.edu.vallegrande.app.db.AccesoDB;
import pe.edu.vallegrande.app.model.Student;
import pe.edu.vallegrande.app.service.spec.CrudServiceSpec;
import pe.edu.vallegrande.app.service.spec.RowMapper2;

public class CrudStudentService implements CrudServiceSpec<Student>, RowMapper2<Student> {

	// Definiendo consultas sql
	private final String SQL_SELECT_BASE = "SELECT student_id, names, lastname, email, document_type, document_number, semester, career, active FROM student";
	private final String SQL_INSERT = "INSERT INTO student (names, lastname, email, document_type, document_number, semester, career) VALUES (?, ?, ?, ?, ?, ?, ?)";
	private final String SQL_UPDATE = "UPDATE student SET names=?, lastname=?, email=?, document_type=?, document_number=? , semester=?, career=? WHERE student_id=?";

	// Obtiene todos los datos de una tabla
	@Override
	public List<Student> getAll() {
		// Variables
		Connection cn = null;
		List<Student> lista = new ArrayList<>();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Student bean;
		// Proceso
		try {
			cn = AccesoDB.getConnection();
			pstm = cn.prepareStatement(SQL_SELECT_BASE);
			rs = pstm.executeQuery();
			while (rs.next()) {
				bean = mapRow2(rs);
				lista.add(bean);
			}
			rs.close();
			pstm.close();
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				cn.close();
			} catch (Exception e2) {
			}
		}
		return lista;
	}

	// Obtiene los datos por el id del registro
	public Student getForId(String id) {
		// Variables
		Connection cn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Student bean = null;
		String sql;
		// Proceso
		try {
			cn = AccesoDB.getConnection();
			sql = SQL_SELECT_BASE + " WHERE student_id=?";
			pstm = cn.prepareStatement(sql);
			pstm.setInt(1, Integer.parseInt(id));
			rs = pstm.executeQuery();
			if (rs.next()) {
				bean = mapRow2(rs);
			}
			rs.close();
			pstm.close();
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				if (cn != null) {
					cn.close();
				}
			} catch (Exception e2) {
				// Manejar excepción al cerrar la conexión
			}
		}
		return bean;
	}

	// Realiza la búsqueda por filtros
	@Override
	public List<Student> get(Student bean) {
		// Variables
		Connection cn = null;
		List<Student> lista = new ArrayList<>();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Student item;
		String sql;
		String names;
		String lastname;
		String email;
		String document_type;
		String document_number;
		String semester;
		String career;
		// Preparar los datos
		names = "%" + UtilService.setStringVacio(bean.getNames()) + "%";
		lastname = "%" + UtilService.setStringVacio(bean.getLastname()) + "%";
		email = "%" + UtilService.setStringVacio(bean.getEmail()) + "%";
		document_type = "%" + UtilService.setStringVacio(bean.getDocument_type()) + "%";
		document_number = "%" + UtilService.setStringVacio(bean.getDocument_number()) + "%";
		semester = "%" + UtilService.setStringVacio(bean.getSemester()) + "%";
		career = "%" + UtilService.setStringVacio(bean.getCareer()) + "%";

		// Proceso
		try {
			// Conexion
			cn = AccesoDB.getConnection();
			// La consulta
			sql = SQL_SELECT_BASE
					+ " WHERE names LIKE ? AND lastname LIKE ? AND email LIKE ? AND document_type LIKE ? AND document_number LIKE ? AND semester LIKE ? AND career LIKE ?";
			pstm = cn.prepareStatement(sql);
			pstm.setString(1, names);
			pstm.setString(2, lastname);
			pstm.setString(3, email);
			pstm.setString(4, document_type);
			pstm.setString(5, document_number);
			pstm.setString(6, semester);
			pstm.setString(7, career);
			rs = pstm.executeQuery();
			while (rs.next()) {
				item = mapRow2(rs);
				lista.add(item);
			}
			rs.close();
			pstm.close();
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				cn.close();
			} catch (Exception e2) {
			}
		}
		return lista;
	}

	// Inserta un nuevo registro
	@Override
	public void insert(Student bean) {
		// Variables
		Connection cn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		// Proceso
		try {
			// Iniciar la Tx
			cn = AccesoDB.getConnection();
			cn.setAutoCommit(false);
			// Insertar nuevo estudiante
			pstm = cn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
			pstm.setString(1, bean.getNames());
			pstm.setString(2, bean.getLastname());
			pstm.setString(3, bean.getEmail());
			pstm.setString(4, bean.getDocument_type());
			pstm.setString(5, bean.getDocument_number());
			pstm.setString(6, bean.getSemester());
			pstm.setString(7, bean.getCareer());
			pstm.executeUpdate();
			// Obtener el ID generado para el nuevo estudiante
			rs = pstm.getGeneratedKeys();
			if (rs.next()) {
				int generatedId = rs.getInt(1);
				bean.setStudent_id(generatedId);
			}
			rs.close();
			pstm.close();
			// Confirmar Tx
			cn.commit();
		} catch (SQLException e) {
			try {
				if (cn != null) {
					cn.rollback();
					cn.close();
				}
			} catch (Exception e2) {
				// Manejar excepción al cerrar la conexión
			}
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				if (cn != null) {
					cn.close();
				}
			} catch (Exception e2) {
				// Manejar excepción al cerrar la conexión
			}
		}
	}
	
	public List<Student> searchByFilter(String filter, String documentType, String active) {
		// Variables
		Connection cn = null;
		
		List<Student> students = new ArrayList<>();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Student student;
		
		String sql = "SELECT "
				+ "	student_id,"
				+ "	names,"
				+ "	lastname,"
				+ "	email,"
				+ "	document_type,"
				+ "	document_number,"
				+ "	semester,"
				+ "	career,"
				+ "	active "
				+ "FROM student "
				+ "WHERE active = ? "
				+ "	AND document_type LIKE ?"
				+ "	AND (names LIKE ?"
				+ "	OR lastname LIKE ?"
				+ "	OR email LIKE ?"
				+ "	OR document_number LIKE ?"
				+ " OR semester LIKE ?"
				+ " OR career LIKE ?)";

		// Proceso
		try {
			// Conexion
			cn = AccesoDB.getConnection();
			pstm = cn.prepareStatement(sql);
			
			pstm.setString(1, active);
			pstm.setString(2, "%" + documentType + "%");
			pstm.setString(3, "%" + filter + "%");
			pstm.setString(4, "%" + filter + "%");
			pstm.setString(5, "%" + filter + "%");
			pstm.setString(6, "%" + filter + "%");
			pstm.setString(7, "%" + filter + "%");
			pstm.setString(8, "%" + filter + "%");
			
			rs = pstm.executeQuery();
			while (rs.next()) {
				student = mapRow2(rs);
				students.add(student);
			}
			rs.close();
			pstm.close();
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				cn.close();
			} catch (Exception e2) {
			}
		}
		return students;
	}

	// Actualiza un registro
	@Override
	public void update(Student bean) {
		// Variables
		Connection cn = null;
		PreparedStatement pstm = null;
		int filas;
		// Proceso
		try {
			// Iniciar la Tx
			cn = AccesoDB.getConnection();
			cn.setAutoCommit(false);
			// Actualizar el registro
			pstm = cn.prepareStatement(SQL_UPDATE);
			pstm.setString(1, bean.getNames());
			pstm.setString(2, bean.getLastname());
			pstm.setString(3, bean.getEmail());
			pstm.setString(4, bean.getDocument_type());
			pstm.setString(5, bean.getDocument_number());
			pstm.setString(6, bean.getSemester());
			pstm.setString(7, bean.getCareer());
			pstm.setInt(8, bean.getStudent_id());
			filas = pstm.executeUpdate();
			pstm.close();
			if (filas != 1) {
				throw new SQLException("Error, verifique sus datos e inténtelo nuevamente.");
			}
			// Fin de Tx
			cn.commit();
		} catch (SQLException e) {
			try {
				if (cn != null) {
					cn.rollback();
					cn.close();
				}
			} catch (Exception e2) {
				// Manejar excepción al cerrar la conexión
			}
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				if (cn != null) {
					cn.close();
				}
			} catch (Exception e2) {
				// Manejar excepción al cerrar la conexión
			}
		}
	}

	// Eliminado logico
	@Override
	public void delete(Integer student_id) {
		// Variables
		Connection cn = null;
		PreparedStatement pstm = null;
		String sql = null;
		// Proceso
		try {
			cn = AccesoDB.getConnection();

			sql = "UPDATE student SET active = 'I' WHERE student_id=?";
			pstm = cn.prepareStatement(sql);
			pstm.setInt(1, student_id);
			pstm.executeUpdate();

			pstm.close();
			cn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//Activado de estudiantes (la resurreccion de freezer)
	public void active(Integer student_id) {
		// Variables
		Connection cn = null;
		PreparedStatement pstm = null;
		String sql = null;
		// Proceso
		try {
			cn = AccesoDB.getConnection();

			sql = "UPDATE student SET active = 'A' WHERE student_id=?";
			pstm = cn.prepareStatement(sql);
			pstm.setInt(1, student_id);
			pstm.executeUpdate();

			pstm.close();
			cn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Student> getInactiveStudents() {
		// Variables
		Connection cn = null;
		List<Student> lista = new ArrayList<>();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Student bean;
		// Proceso
		try {
			cn = AccesoDB.getConnection();
			String sql = SQL_SELECT_BASE + " WHERE active = 'I'";
			pstm = cn.prepareStatement(sql);
			rs = pstm.executeQuery();
			while (rs.next()) {
				bean = mapRow2(rs);
				lista.add(bean);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstm != null) {
					pstm.close();
				}
				if (cn != null) {
					cn.close();
				}
			} catch (SQLException e) {
				throw new RuntimeException(e.getMessage());
			}
		}
		return lista;
	}

	public List<Student> getActiveStudents() {
		// Variables
		Connection cn = null;
		List<Student> lista = new ArrayList<>();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Student bean;
		// Proceso
		try {
			cn = AccesoDB.getConnection();
			String sql = SQL_SELECT_BASE + " WHERE active = 'A'";
			pstm = cn.prepareStatement(sql);
			rs = pstm.executeQuery();
			while (rs.next()) {
				bean = mapRow2(rs);
				lista.add(bean);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstm != null) {
					pstm.close();
				}
				if (cn != null) {
					cn.close();
				}
			} catch (SQLException e) {
				throw new RuntimeException(e.getMessage());
			}
		}
		return lista;
	}

	public List<Student> searchByNames(String searchValue) {
		// Variables
		Connection cn = null;
		List<Student> studentList = new ArrayList<>();
		PreparedStatement pstm = null;
		String sql = "SELECT * FROM student WHERE active = 'A' AND names LIKE ?";

		try {
			cn = AccesoDB.getConnection();
			pstm = cn.prepareStatement(sql);
			pstm.setString(1, "%" + searchValue + "%");
			ResultSet resultSet = pstm.executeQuery();

			while (resultSet.next()) {
				Student student = new Student();
				student.setStudent_id(resultSet.getInt("student_id"));
				student.setNames(resultSet.getString("names"));
				student.setLastname(resultSet.getString("lastname"));
				student.setEmail(resultSet.getString("email"));;
				student.setDocument_type(resultSet.getString("document_type"));
				student.setDocument_number(resultSet.getString("document_number"));
				student.setSemester(resultSet.getString("semester"));
				student.setCareer(resultSet.getString("career"));
				student.setActive(resultSet.getString("active"));

				studentList.add(student);
			}
			resultSet.close();
			pstm.close();
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				cn.close();
			} catch (Exception e2) {
			}
		}
		return studentList;
	}

	public List<Student> searchByLastname(String searchValue) {
		// Variables
		Connection cn = null;
		List<Student> studentList = new ArrayList<>();
		PreparedStatement pstm = null;
		String sql = "SELECT * FROM student WHERE lastname LIKE ?";

		try {
			cn = AccesoDB.getConnection();
			pstm = cn.prepareStatement(sql);
			pstm.setString(1, "%" + searchValue + "%");
			ResultSet resultSet = pstm.executeQuery();

			while (resultSet.next()) {
				Student student = new Student();
				student.setStudent_id(resultSet.getInt("student_id"));
				student.setNames(resultSet.getString("names"));
				student.setLastname(resultSet.getString("lastname"));
				student.setEmail(resultSet.getString("email"));
				student.setDocument_type(resultSet.getString("document_type"));
				student.setDocument_number(resultSet.getString("document_number"));
				student.setSemester(resultSet.getString("semester"));
				student.setCareer(resultSet.getString("career"));
				student.setActive(resultSet.getString("active"));

				studentList.add(student);
			}
			resultSet.close();
			pstm.close();
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				cn.close();
			} catch (Exception e2) {
			}
		}
		return studentList;
	}

	public List<Student> searchByDocument_type(String searchValue) {
		// Variables
		Connection cn = null;
		List<Student> studentList = new ArrayList<>();
		PreparedStatement pstm = null;
		String sql = "SELECT * FROM student WHERE document_type = ?";

		try {
			cn = AccesoDB.getConnection();
			pstm = cn.prepareStatement(sql);
			pstm.setString(1, searchValue);
			ResultSet resultSet = pstm.executeQuery();

			while (resultSet.next()) {
				Student student = new Student();
				student.setStudent_id(resultSet.getInt("student_id"));
				student.setNames(resultSet.getString("names"));
				student.setLastname(resultSet.getString("lastname"));
				student.setEmail(resultSet.getString("email"));
				student.setDocument_type(resultSet.getString("document_type"));
				student.setDocument_number(resultSet.getString("document_number"));
				student.setSemester(resultSet.getString("semester"));
				student.setCareer(resultSet.getString("career"));
				student.setActive(resultSet.getString("active"));

				studentList.add(student);
			}
			pstm.close();
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				cn.close();
			} catch (Exception e2) {
			}
		}
		return studentList;
	}

	public List<Student> searchByDocument_number(String searchValue) {
		// Variables
		Connection cn = null;
		List<Student> studentList = new ArrayList<>();
		PreparedStatement pstm = null;
		String sql = "SELECT * FROM student WHERE document_number = ?";
		try {
			cn = AccesoDB.getConnection();
			pstm = cn.prepareStatement(sql);
			pstm.setString(1, searchValue);
			ResultSet resultSet = pstm.executeQuery();

			while (resultSet.next()) {
				Student student = new Student();
				student.setStudent_id(resultSet.getInt("student_id"));
				student.setNames(resultSet.getString("names"));
				student.setLastname(resultSet.getString("lastname"));
				student.setEmail(resultSet.getString("email"));
				student.setDocument_type(resultSet.getString("document_type"));
				student.setDocument_number(resultSet.getString("document_number"));
				student.setSemester(resultSet.getString("semester"));
				student.setCareer(resultSet.getString("career"));
				student.setActive(resultSet.getString("active"));

				studentList.add(student);
			}
			resultSet.close();
			pstm.close();
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				cn.close();
			} catch (Exception e2) {
			}
		}
		return studentList;
	}
	// Eliminado
	@Override
	public void delete(String id) {

	}

	@Override
	public Student getForId(Integer id) {
		return null;
	}

	// Mapeado de listas
	@Override
	public Student mapRow2(ResultSet rs) throws SQLException {
		Student bean = new Student();
		// Columnas: student_id, names, lastname, document_type, document_number, active
		bean.setStudent_id(rs.getInt("student_id"));
		bean.setNames(rs.getString("names"));
		bean.setLastname(rs.getString("lastname"));
		bean.setEmail(rs.getString("email"));
		bean.setDocument_type(rs.getString("document_type"));
		bean.setDocument_number(rs.getString("document_number"));
		bean.setSemester(rs.getString("semester"));
		bean.setCareer(rs.getString("career"));
		bean.setActive(rs.getString("active"));
		return bean;
	}

	public List<Student> searchByFil(String filter, String documentType, String active) {
		// Variables
		Connection cn = null;
		
		List<Student> students = new ArrayList<>();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Student student;
		
		String sql = "SELECT "
				+ "	student_id,"
				+ "	names,"
				+ "	lastname,"
				+ "	email,"
				+ "	document_type,"
				+ "	document_number,"
				+ "	semester,"
				+ "	career,"
				+ "	active "
				+ "FROM student "
				+ "WHERE active = ? "
				+ "	AND document_type LIKE ?"
				+ "	AND (names LIKE ?"
				+ "	OR lastname LIKE ?"
				+ "	OR email LIKE ?"
				+ "	OR document_number LIKE ?"
				+ " OR semester LIKE ?"
				+ " OR career LIKE ?)";

		// Proceso
		try {
			// Conexion
			cn = AccesoDB.getConnection();
			pstm = cn.prepareStatement(sql);
			
			pstm.setString(1, active);
			pstm.setString(2, "%" + documentType + "%");
			pstm.setString(3, "%" + filter + "%");
			pstm.setString(4, "%" + filter + "%");
			pstm.setString(5, "%" + filter + "%");
			pstm.setString(6, "%" + filter + "%");
			pstm.setString(7, "%" + filter + "%");
			pstm.setString(8, "%" + filter + "%");
			
			rs = pstm.executeQuery();
			while (rs.next()) {
				student = mapRow2(rs);
				students.add(student);
			}
			rs.close();
			pstm.close();
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				cn.close();
			} catch (Exception e2) {
			}
		}
		return students;
	}


}



