package pe.edu.vallegrande.app.service.spec;

import java.util.List;

import pe.edu.vallegrande.app.model.Student;

public interface CrudServiceSpec<T> {
	
	/**
	 * Consulta todos los registros de la tabla.
	 * @return Retorna una lista.
	 */
	List<T> getAll();
	
	/**
	 * Retorna un registro basado en el id.
	 * @param id
	 * @return
	 */

	T getForId(Integer id);
	
	/**
	 * Consulta datos en base3 a un criterio.

	T getForId(String id);

	/**
	 * Consulta datos en base a un criterio.
	 * @param bean Datos para establecer el criterio.
	 * @return Retorna una lista.
	 */
	List<T> get(T bean);

	/**

	 * Inserta nuevo registro.
	 * @param bean Datos del nuevo registro 
	 */
	void insert(T bean);
	

	/**
	 * Actualiza datos de un registro especifico.
	 * @param bean Datos del registro.
	 */
	void update(T bean);

	
	/**
	 * Elminar un registro de la base de datos.
	 * @param id El id del registro a eliminar.
	 */
	void delete(Integer id);
	


	/**
	 * Elimina un registro de la base de datos.
	 * @param id El id del registro a eliminar.
	 */
	void delete(String id);

	void active(Integer id);

}
