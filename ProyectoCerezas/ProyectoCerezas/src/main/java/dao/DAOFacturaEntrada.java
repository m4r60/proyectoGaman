package dao;

import java.util.Date;
import java.util.List;

import modelos.FacturaEntrada;

public interface DAOFacturaEntrada {
	public boolean create(FacturaEntrada fe);
	public FacturaEntrada read(int nFactura);
	public FacturaEntrada readConDetalles(int nFactura);
	public boolean update(FacturaEntrada fe);
	public List<FacturaEntrada> listar();
	public List<FacturaEntrada> listar(String cifNif);
	public List<FacturaEntrada> listar(int nFactura);
	//public List<FacturaEntrada> listar(Date fecha);
	public List<FacturaEntrada> buscarFecha (Date fechaInicio, Date fechaFinal);
	public boolean anularFactura(int nFactura);
	public boolean delete(int nFactura); //Se usa para los JUnit
	public double calcularPrecioFactura(int nFactura);
	public void meterDni(FacturaEntrada fs);
	public DAOAlbaranEntrada getDaoAlbaran();
	public void setDaoAlbaran(DAOAlbaranEntrada daoAlbaran);
	
}
