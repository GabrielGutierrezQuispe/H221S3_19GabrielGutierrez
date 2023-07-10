package pe.edu.vallegrande.app.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pe.edu.vallegrande.app.db.AccesoDB;
import pe.edu.vallegrande.app.model.tesorero;
import pe.edu.vallegrande.app.service.spec.CrudServiceSpec;
import pe.edu.vallegrande.app.service.spec.RowMapper;

public class CrudTesoreroService implements CrudServiceSpec<tesorero>, RowMapper<tesorero> {

	// Definiendo consultas sql
	private final String SQL_SELECT_BASE = "SELECT administrative_id, names, lastname, email, document_type, document_number, passwords, active FROM administrative";
	private final String SQL_INSERT = "INSERT INTO administrative (names, lastname, email, document_type, document_number, passwords) VALUES (?, ?, ?, ?, ?, ?)";
	private final String SQL_UPDATE = "UPDATE administrative SET names=?, lastname=?, email=?, document_type=?, document_number=? , passwords=? WHERE administrative_id=?";

	// Obtiene todos los datos de una tabla
	@Override
	public List<tesorero> getAll() {
		// Variables
		Connection cn = null;
		List<tesorero> lista = new ArrayList<>();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		tesorero bean;
		// Proceso
		try {
			cn = AccesoDB.getConnection();
			pstm = cn.prepareStatement(SQL_SELECT_BASE);
			rs = pstm.executeQuery();
			while (rs.next()) {
				bean = mapRow(rs);
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
	public tesorero getForId(String id) {
		// Variables
		Connection cn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		tesorero bean = null;
		String sql;
		// Proceso
		try {
			cn = AccesoDB.getConnection();
			sql = SQL_SELECT_BASE + " WHERE administrative_id=?";
			pstm = cn.prepareStatement(sql);
			pstm.setInt(1, Integer.parseInt(id));
			rs = pstm.executeQuery();
			if (rs.next()) {
				bean = mapRow(rs);
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
	public List<tesorero> get(tesorero bean) {
		// Variables
		Connection cn = null;
		List<tesorero> lista = new ArrayList<>();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		tesorero item;
		String sql;
		String names;
		String lastname;
		String email;
		String document_type;
		String document_number;
		String passwords;
		// Preparar los datos
		names = "%" + UtilService.setStringVacio(bean.getNames()) + "%";
		lastname = "%" + UtilService.setStringVacio(bean.getLastname()) + "%";
		email = "%" + UtilService.setStringVacio(bean.getEmail()) + "%";
		document_type = "%" + UtilService.setStringVacio(bean.getDocument_type()) + "%";
		document_number = "%" + UtilService.setStringVacio(bean.getDocument_number()) + "%";
		passwords = "%" + UtilService.setStringVacio(bean.getPasswords()) + "%";

		// Proceso
		try {
			// Conexion
			cn = AccesoDB.getConnection();
			// La consulta
			sql = SQL_SELECT_BASE
					+ " WHERE names LIKE ? AND lastname LIKE ? AND email LIKE ? AND document_type LIKE ? AND document_number LIKE ? AND passwords LIKE ?";
			pstm = cn.prepareStatement(sql);
			pstm.setString(1, names);
			pstm.setString(2, lastname);
			pstm.setString(3, email);
			pstm.setString(4, document_type);
			pstm.setString(5, document_number);
			pstm.setString(6, passwords);
			rs = pstm.executeQuery();
			while (rs.next()) {
				item = mapRow(rs);
				lista.add(item); // Fix: Add 'item' instead of 'bean' to the list
			}
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				// Close result set, prepared statement, and connection in a proper order
				if (rs != null) {
					rs.close();
				}
				if (pstm != null) {
					pstm.close();
				}
				if (cn != null) {
					cn.close();
				}
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
		return lista;
	}

	// Inserta un nuevo registro
	@Override
	public void insert(tesorero bean) {
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
			pstm.setString(6, bean.getPasswords());
			pstm.executeUpdate();
			// Obtener el ID generado para el nuevo estudiante
			rs = pstm.getGeneratedKeys();
			if (rs.next()) {
				int generatedId = rs.getInt(1);
				bean.setAdministrative_id(generatedId);
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

	public List<tesorero> searchByFilter(String filter, String documentType, String active) {
		// Variables
		Connection cn = null;

		List<tesorero> tesoreros = new ArrayList<>();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		tesorero tesorero;

		String sql = "SELECT " + "	administrative_id," + "	names," + "	lastname," + "	email," + "	document_type,"
				+ "	document_number," + "	passwords," + "	active " + "FROM administrative " + "WHERE active = ? "
				+ "	AND document_type LIKE ?" + "	AND (names LIKE ?" + "	OR lastname LIKE ?" + "	OR email LIKE ?"
				+ "	OR document_number LIKE ?" + " OR passwords LIKE ?)";

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

			rs = pstm.executeQuery();
			while (rs.next()) {
				tesorero = mapRow(rs);
				tesoreros.add(tesorero);
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
		return tesoreros;
	}

	// Actualiza un registro
	@Override
	public void update(tesorero bean) {
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
			pstm.setString(6, bean.getPasswords());
			pstm.setInt(7, bean.getAdministrative_id());
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
	public void delete(Integer administrative_id) {
		// Variables
		Connection cn = null;
		PreparedStatement pstm = null;
		String sql = null;
		// Proceso
		try {
			cn = AccesoDB.getConnection();

			sql = "UPDATE administrative SET active = 'I' WHERE administrative_id=?";
			pstm = cn.prepareStatement(sql);
			pstm.setInt(1, administrative_id);
			pstm.executeUpdate();

			pstm.close();
			cn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Activado de estudiantes (la resurreccion de freezer)
	public void active(Integer administrative_id) {
		// Variables
		Connection cn = null;
		PreparedStatement pstm = null;
		String sql = null;
		// Proceso
		try {
			cn = AccesoDB.getConnection();

			sql = "UPDATE administrative SET active = 'A' WHERE administrative_id=?";
			pstm = cn.prepareStatement(sql);
			pstm.setInt(1, administrative_id);
			pstm.executeUpdate();

			pstm.close();
			cn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<tesorero> getInactiveStudents() {
		// Variables
		Connection cn = null;
		List<tesorero> lista = new ArrayList<>();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		tesorero bean;
		// Proceso
		try {
			cn = AccesoDB.getConnection();
			String sql = SQL_SELECT_BASE + " WHERE active = 'I'";
			pstm = cn.prepareStatement(sql);
			rs = pstm.executeQuery();
			while (rs.next()) {
				bean = mapRow(rs);
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

	public List<tesorero> getActiveStudents() {
		// Variables
		Connection cn = null;
		List<tesorero> lista = new ArrayList<>();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		tesorero bean;
		// Proceso
		try {
			cn = AccesoDB.getConnection();
			String sql = SQL_SELECT_BASE + " WHERE active = 'A'";
			pstm = cn.prepareStatement(sql);
			rs = pstm.executeQuery();
			while (rs.next()) {
				bean = mapRow(rs);
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

	public List<tesorero> searchByNames(String searchValue) {
		// Variables
		Connection cn = null;
		List<tesorero> tesoreroList = new ArrayList<>();
		PreparedStatement pstm = null;
		String sql = "SELECT * FROM administrative WHERE active = 'A' AND names LIKE ?";

		try {
			cn = AccesoDB.getConnection();
			pstm = cn.prepareStatement(sql);
			pstm.setString(1, "%" + searchValue + "%");
			ResultSet resultSet = pstm.executeQuery();

			while (resultSet.next()) {
				tesorero tesorer = new tesorero();
				tesorer.setAdministrative_id(resultSet.getInt("administrative_id"));
				tesorer.setNames(resultSet.getString("names"));
				tesorer.setLastname(resultSet.getString("lastname"));
				tesorer.setEmail(resultSet.getString("email"));
				;
				tesorer.setDocument_type(resultSet.getString("document_type"));
				tesorer.setDocument_number(resultSet.getString("document_number"));
				tesorer.setPasswords(resultSet.getString("passwords"));
				tesorer.setActive(resultSet.getString("active"));

				tesoreroList.add(tesorer);
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
		return tesoreroList;
	}

	public List<tesorero> searchByLastname(String searchValue) {
		// Variables
		Connection cn = null;
		List<tesorero> tesoreroList = new ArrayList<>();
		PreparedStatement pstm = null;
		String sql = "SELECT * FROM administrative WHERE lastname LIKE ?";

		try {
			cn = AccesoDB.getConnection();
			pstm = cn.prepareStatement(sql);
			pstm.setString(1, "%" + searchValue + "%");
			ResultSet resultSet = pstm.executeQuery();

			while (resultSet.next()) {
				tesorero tesorer = new tesorero();
				tesorer.setAdministrative_id(resultSet.getInt("administrative_id"));
				tesorer.setNames(resultSet.getString("names"));
				tesorer.setLastname(resultSet.getString("lastname"));
				tesorer.setEmail(resultSet.getString("email"));
				tesorer.setDocument_type(resultSet.getString("document_type"));
				tesorer.setDocument_number(resultSet.getString("document_number"));
				tesorer.setPasswords(resultSet.getString("passwords"));
				tesorer.setActive(resultSet.getString("active"));

				tesoreroList.add(tesorer);
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
		return tesoreroList;
	}

	public List<tesorero> searchByDocument_type(String searchValue) {
		// Variables
		Connection cn = null;
		List<tesorero> tesoreroList = new ArrayList<>();
		PreparedStatement pstm = null;
		String sql = "SELECT * FROM administrative WHERE document_type = ?";

		try {
			cn = AccesoDB.getConnection();
			pstm = cn.prepareStatement(sql);
			pstm.setString(1, searchValue);
			ResultSet resultSet = pstm.executeQuery();

			while (resultSet.next()) {
				tesorero tesorer = new tesorero();
				tesorer.setAdministrative_id(resultSet.getInt("administrative_id"));
				tesorer.setNames(resultSet.getString("names"));
				tesorer.setLastname(resultSet.getString("lastname"));
				tesorer.setEmail(resultSet.getString("email"));
				tesorer.setDocument_type(resultSet.getString("document_type"));
				tesorer.setDocument_number(resultSet.getString("document_number"));
				tesorer.setPasswords(resultSet.getString("passwords"));
				tesorer.setActive(resultSet.getString("active"));

				tesoreroList.add(tesorer);
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
		return tesoreroList;
	}

	public List<tesorero> searchByDocument_number(String searchValue) {
		// Variables
		Connection cn = null;
		List<tesorero> tesoreroList = new ArrayList<>();
		PreparedStatement pstm = null;
		String sql = "SELECT * FROM administrative WHERE document_number = ?";
		try {
			cn = AccesoDB.getConnection();
			pstm = cn.prepareStatement(sql);
			pstm.setString(1, searchValue);
			ResultSet resultSet = pstm.executeQuery();

			while (resultSet.next()) {
				tesorero tesorer = new tesorero();
				tesorer.setAdministrative_id(resultSet.getInt("administrative_id"));
				tesorer.setNames(resultSet.getString("names"));
				tesorer.setLastname(resultSet.getString("lastname"));
				tesorer.setEmail(resultSet.getString("email"));
				tesorer.setDocument_type(resultSet.getString("document_type"));
				tesorer.setDocument_number(resultSet.getString("document_number"));
				tesorer.setPasswords(resultSet.getString("passwords"));
				tesorer.setActive(resultSet.getString("active"));

				tesoreroList.add(tesorer);
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
		return tesoreroList;
	}

	// Eliminado
	@Override
	public void delete(String id) {

	}

	@Override
	public tesorero getForId(Integer id) {
		return null;
	}

	// Mapeado de listas
	@Override
	public tesorero mapRow(ResultSet rs) throws SQLException {
		tesorero bean = new tesorero();
		// Columnas: student_id, names, lastname, document_type, document_number, active
		bean.setAdministrative_id(rs.getInt("administrative_id"));
		bean.setNames(rs.getString("names"));
		bean.setLastname(rs.getString("lastname"));
		bean.setEmail(rs.getString("email"));
		bean.setDocument_type(rs.getString("document_type"));
		bean.setDocument_number(rs.getString("document_number"));
		bean.setPasswords(rs.getString("passwords"));
		bean.setActive(rs.getString("active"));
		return bean;
	}

	public List<tesorero> searchByFil(String filter, String documentType, String active) {
		// Variables
		Connection cn = null;

		List<tesorero> tesoreros = new ArrayList<>();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		tesorero tesorer;

		String sql = "SELECT " + "	administrative_id," + "	names," + "	lastname," + "	email," + "	document_type,"
				+ "	document_number," + "	passwords," + "	active " + "FROM administrative " + "WHERE active = ? "
				+ "	AND document_type LIKE ?" + "	AND (names LIKE ?" + "	OR lastname LIKE ?" + "	OR email LIKE ?"
				+ "	OR document_number LIKE ?" + " OR passwords LIKE ?)";

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

			rs = pstm.executeQuery();
			while (rs.next()) {
				tesorer = mapRow(rs);
				tesoreros.add(tesorer);
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
		return tesoreros;
	}

}
