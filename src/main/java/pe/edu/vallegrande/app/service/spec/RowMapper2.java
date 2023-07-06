package pe.edu.vallegrande.app.service.spec;

import java.sql.ResultSet;
import java.sql.SQLException;

import pe.edu.vallegrande.app.model.Student;

public interface RowMapper2<T> {

	Student mapRow2(ResultSet rs) throws SQLException;
	
}
