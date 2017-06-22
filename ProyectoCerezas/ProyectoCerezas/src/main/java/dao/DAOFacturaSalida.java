package dao;

import java.util.Date;
import java.util.List;

import modelos.FacturaSalida;

public interface DAOFacturaSalida {
		public boolean create(FacturaSalida fs);
		public FacturaSalida read(int nFactura);
		public FacturaSalida readConDetalles(int nFactura);
		public boolean update(FacturaSalida fe);
		public List<FacturaSalida> listar();
		public List<FacturaSalida> listar(String cifNif);
		public List<FacturaSalida> listar(int nFactura);
		//public List<FacturaSalida> listar(Date fecha);
		public List<FacturaSalida> buscarFecha (Date fechaInicio, Date fechaFinal);
		public boolean anularFactura(int nFactura);
		public boolean delete(int nFactura); //Se usa en los JUnit
		public double calcularPrecioFactura(int nFactura);
		public void meterDni(FacturaSalida fs);
		public DAOAlbaranSalida getDaoAlbaran();
		public void setDaoAlbaran(DAOAlbaranSalida daoAlbaran);

}
