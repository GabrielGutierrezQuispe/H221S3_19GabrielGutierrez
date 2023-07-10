package pe.edu.vallegrande.app.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import pe.edu.vallegrande.app.db.AccesoDB;
import pe.edu.vallegrande.app.model.Student;
import pe.edu.vallegrande.app.model.payment;
import pe.edu.vallegrande.app.service.spec.CrudServiceSpec;
import pe.edu.vallegrande.app.service.spec.RowMapper3;

public class CrudPaymentService implements CrudServiceSpec<payment>, RowMapper3<payment> {

	// Definiendo consultas sql
	private final String SQL_SELECT_BASE = "SELECT id, type_payment, amount, dates, reference_number, term_time, states FROM payment";
	private final String SQL_INSERT = "INSERT INTO payment (type_payment, amount, reference_number, states) VALUES (?, ?, ?, ?)";
	private final String SQL_UPDATE = "UPDATE payment SET type_payment=?, amount=?, reference_number=?, states=? WHERE id=?";

	// Listado general
	@Override
	public List<payment> getAll() {
		// Variables
		Connection cn = null;
		List<payment> lista = new ArrayList<>();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		payment bean;
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

	// Realiza la búsqueda por filtros
	@Override
	public List<payment> get(payment bean) {
		// Variables
		Connection cn = null;
		List<payment> lista = new ArrayList<>();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		payment item;
		String sql;
		String type_payment;
		String dates;
		String reference_number;
		String states;
		// Preparar los datos
		type_payment = "%" + UtilService.setStringVacio(bean.getTypePayment()) + "%";
		dates = "%" + UtilService.setStringVacio(bean.getDates()) + "%";
		reference_number = "%" + UtilService.setStringVacio(bean.getReferenceNumber()) + "%";
		states = "%" + UtilService.setStringVacio(bean.getStates()) + "%";
		// Proceso
		try {
			// Conexion
			cn = AccesoDB.getConnection();
			// La consulta
			sql = SQL_SELECT_BASE
					+ " WHERE type_payment LIKE ? AND dates LIKE ? AND reference_number LIKE ? AND states LIKE ?";
			pstm = cn.prepareStatement(sql);
			pstm.setString(1, type_payment);
			pstm.setString(2, dates);
			pstm.setString(3, reference_number);
			pstm.setString(4, states);
			rs = pstm.executeQuery();
			while (rs.next()) {
				item = mapRow(rs);
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

	// Insertar pago
	@Override
	public void insert(payment bean) {
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
			pstm.setString(1, bean.getTypePayment());
			pstm.setString(2, bean.getAmount());
			pstm.setString(3, bean.getReferenceNumber());
			pstm.setString(4, bean.getStates());
			pstm.executeUpdate();
			// Obtener el ID generado para el nuevo estudiante
			rs = pstm.getGeneratedKeys();
			if (rs.next()) {
				int generatedId = rs.getInt(1);
				bean.setId(generatedId);
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

	@Override
	public void update(payment bean) {
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
			pstm.setString(1, bean.getTypePayment());
			pstm.setString(2, bean.getAmount());
			pstm.setString(3, bean.getReferenceNumber());
			pstm.setString(4, bean.getStates());
			pstm.setInt(5, bean.getId());
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

	@Override
	public payment getForId(Integer id) {
		// Variables
		Connection cn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		payment bean = null;
		String sql;
		// Proceso
		try {
			cn = AccesoDB.getConnection();
			sql = SQL_SELECT_BASE + " WHERE id = ? ";
			pstm = cn.prepareStatement(sql);
			pstm.setInt(1, Integer.parseInt(sql));
//			pstm.setInt(1, Integer.parseInt(id));
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

	@Override
	public void delete(Integer id) {
		// Variables
		Connection cn = null;
		PreparedStatement pstm = null;
		String sql = null;
		// Proceso
		try {
			cn = AccesoDB.getConnection();

			sql = "UPDATE payment SET states = 'C' WHERE id=?";
			pstm = cn.prepareStatement(sql);
			pstm.setInt(1, id);
			pstm.executeUpdate();

			pstm.close();
			cn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	public List<payment> searchByFil(String filter, String states) {
		// Variables
		Connection cn = null;
		
		List<payment> paymentss = new ArrayList<>();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		payment payments;
		
		String sql = "SELECT "
				+ "	id,"
				+ "	type_Payment,"
				+ "	amount,"
				+ "	dates,"
				+ "	reference_number,"
				+ "	term_time,"
				+ "	states,"
				+ "FROM payment "
				+ "WHERE states = ? "
				+ "	AND (type_Payment LIKE ?"
				+ "	OR amount LIKE ?"
				+ "	OR dates LIKE ?"
				+ "	OR reference_number LIKE ?"
				+ " OR term_time LIKE ?)";

		// Proceso
		try {
			// Conexion
			cn = AccesoDB.getConnection();
			pstm = cn.prepareStatement(sql);
			
			pstm.setString(1, states);
			pstm.setString(2, "%" + filter + "%");
			pstm.setString(3, "%" + filter + "%");
			pstm.setString(4, "%" + filter + "%");
			pstm.setString(5, "%" + filter + "%");
			pstm.setString(6, "%" + filter + "%");
			
			rs = pstm.executeQuery();
			while (rs.next()) {
				payments = mapRow(rs);
				paymentss.add(payments);
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
		return paymentss;
	}
	
	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void active(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public payment mapRow(ResultSet rs) throws SQLException {
		payment bean = new payment();
		// Columnas
		bean.setId(rs.getInt("id"));
		bean.setTypePayment(rs.getString("type_payment"));
		bean.setAmount(rs.getString("amount"));
		bean.setDates(rs.getString("dates"));
		bean.setReferenceNumber(rs.getString("reference_number"));
		bean.setTermTime(rs.getString("term_time"));
		bean.setStates(rs.getString("states"));
		return bean;
	}

}
