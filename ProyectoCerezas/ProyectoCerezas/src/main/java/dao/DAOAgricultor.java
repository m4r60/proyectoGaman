package dao;

import java.util.List;

import javax.sql.DataSource;

import modelos.Agricultor;

public interface DAOAgricultor {
	
	public DataSource getDataSource();
	public void setDataSource(DataSource dataSource);
	
	public boolean create(final Agricultor a);
	public Agricultor read(int idPersona);
	public Agricultor readModificar(int nSocio);
	public List<Agricultor> listar(String busqueda);
	public boolean update(Agricultor c);
	public List<Agricultor> listar();
	public boolean baja (Agricultor c);
	public boolean delete(int nSocio);
}
