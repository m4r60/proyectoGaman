package dao;

import javax.sql.DataSource;
import modelos.Persona;

public interface DAOPersona {
	/*Base de Datos*/
	public DataSource getDataSource();
	public void setDataSource(DataSource dataSource);
	/*CRUD*/
	public boolean create(Persona p);
	public Persona read(String cifNif);
	public boolean delete(int idPersona); // Borrar se usa en JUnit
	
}