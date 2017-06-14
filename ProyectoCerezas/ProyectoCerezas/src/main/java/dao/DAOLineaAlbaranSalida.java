package dao;

import java.util.List;

import javax.sql.DataSource;

import modelos.LineaAlbaranSalida;


public interface DAOLineaAlbaranSalida {
	/*Conexión con la base de datos*/
	public DataSource getDataSource();
	public void setDataSource(DataSource dataSource);
	/*CRUD y listar*/
	public boolean create(final LineaAlbaranSalida lae);
	public LineaAlbaranSalida read(int idLinea);
	public boolean update(LineaAlbaranSalida lae);
	public List<LineaAlbaranSalida> listar(int nAlbaran);
	public boolean delete(int idLinea);

}
