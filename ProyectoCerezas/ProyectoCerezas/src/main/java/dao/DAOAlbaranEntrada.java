package dao;

import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import modelos.AlbaranEntrada;

public interface DAOAlbaranEntrada {
	/*Base de datos*/
	public DataSource getDataSource();
	public void setDataSource(DataSource dataSource);
	/*CRUD*/
	public boolean create(final AlbaranEntrada a); /*Hay que ver cuál de los dos métodos crear es mejor.*/
	public AlbaranEntrada read(int nAlbaran);
	public boolean update(AlbaranEntrada ae);
	public boolean facturar(int nAlbaran, int nFactura);
	public List<AlbaranEntrada> listar();
	public List<AlbaranEntrada> listar(String cifNif);
	public List<AlbaranEntrada> listar(Date fecha);
	public List<AlbaranEntrada> buscarFecha (Date fechaInicio, Date fechaFinal);
	public List<AlbaranEntrada> listarPendientes(String cifNif);
	public boolean delete(int nAlbaran);
	public double calcularPrecioE(int nAlbaran);
	public double calcularPrecioUnaLineaE (int idLinea);
	public AlbaranEntrada readConDetalles(int nAlbaran);
	public List<AlbaranEntrada> listarConDetalle(int nFactura);
	public DAOLineaAlbaranEntrada getDaoLineas();
	public void setDaoLineas(DAOLineaAlbaranEntrada daoLineas);
	
}
