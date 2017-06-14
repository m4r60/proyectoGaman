package dao;

import java.util.List;

import javax.sql.DataSource;

import modelos.LineaAlbaranEntrada;

public interface DAOLineaAlbaranEntrada {
	/*Conexión con la base de datos*/
	public DataSource getDataSource();
	public void setDataSource(DataSource dataSource);
	/*CRUD y listar*/
	public boolean create(LineaAlbaranEntrada lae);
	public LineaAlbaranEntrada read(int idLinea);
	public boolean update(LineaAlbaranEntrada lae);
	public List<LineaAlbaranEntrada> listar(int nAlbaran);
	public boolean delete(int idLinea);
}
