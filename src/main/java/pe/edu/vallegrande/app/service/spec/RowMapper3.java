package pe.edu.vallegrande.app.service.spec;

import java.sql.ResultSet;
import java.sql.SQLException;

import pe.edu.vallegrande.app.model.payment;

public interface RowMapper3<T> {

	payment mapRow(ResultSet rs) throws SQLException;
	
}
