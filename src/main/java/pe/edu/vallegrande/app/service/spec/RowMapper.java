package pe.edu.vallegrande.app.service.spec;

import java.sql.ResultSet;
import java.sql.SQLException;

import pe.edu.vallegrande.app.model.Student;
import pe.edu.vallegrande.app.model.tesorero;

public interface RowMapper<T> {

	tesorero mapRow(ResultSet rs) throws SQLException;
	
}
