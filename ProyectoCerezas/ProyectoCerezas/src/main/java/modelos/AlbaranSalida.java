package modelos;

import java.util.Date;
import java.util.List;

import Utils.DateUtils;

public class AlbaranSalida extends Cliente { /*Si heredo de cliente puedo conectar todas las tablas para en Factura salida conseguir llegar a recuperar cif_nif*/
	/*Quizás tenga que eliminar nCliente porque se hereda de la tabla cliente*/
	/**										 
	 * PROPIEDADES
	 */
	private int nAlbaran;
	private int nCliente;
	private Date fecha; 
	private int nFactura;
	
	/******************************************************************************/
	/*Meto estas propiedades para poder mostrarlas dentro de albaranes salida*/
	private String cifNif;
	private double precioNeto;
	private String fechaStr;
	private List<LineaAlbaranSalida> lineas;
	/******************************************************************************/
	/**
	 * CONSTRUCTORES
	 */
	public AlbaranSalida(){}
	
	/**
	 * CONSTRUCTOR PARA EL FORMULARIO
	 * @param nCliente
	 * @param fecha
	 * @param nFactura
	 * 
	 */
	public AlbaranSalida(int nCliente, Date fecha, int nFactura){
		this.nCliente = nCliente;
		this.fecha = fecha;
		this.nFactura = nFactura;	
	}
	
	/**
	 * CONSTRUCTOR PARA EL ROWMAPPER
	 * @param nCliente
	 * @param fecha
	 * @param nFactura
	 * @param nAlbaran
	 */
	public AlbaranSalida(int nAlbaran,int nCliente, Date fecha, int nFactura){
		this.nCliente = nCliente;
		this.nAlbaran = nAlbaran;
		this.fecha = fecha;
		this.nFactura = nFactura;	
	}
	
	/*Constructor listar Albaranes de salida*/
	
	public AlbaranSalida (int nAlbaran,  int nCliente, Date fecha, int nFactura,  String cifNif){
		this.nCliente = nCliente;
		this.nAlbaran = nAlbaran;
		this.fecha = fecha;
		this.nFactura = nFactura;
		this.cifNif = cifNif;
	}
	
	
	
	/**
	 * GETTER AND SETTER
	 */
	
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getStringFecha(){
		return DateUtils.formatearFecha(fecha);
	}
	public int getnFactura() {
		return nFactura;
	}
	public void setnFactura(int nFactura) {
		this.nFactura = nFactura;
	}
	public int getnAlbaran() {
		return nAlbaran;
	}

	public void setnAlbaran(int nAlbaran) {
		this.nAlbaran = nAlbaran;
	}


	public int getnCliente() {
		return nCliente;
	}

	public void setnCliente(int nCliente) {
		this.nCliente = nCliente;
	}
	/***************************************************************/
	/**GETTERS Y SETTERS DE LAS PROPIEDADES NUEVAS*********/
	public String getCifNif() {
		return cifNif;
	}

	public void setCifNif(String cifNif) {
		this.cifNif = cifNif;
	}

	public double getPrecioNeto() {
		return precioNeto;
	}

	public void setPrecioNeto(double precioNeto) {
		this.precioNeto = precioNeto;
	}

	public String getFechaStr() {
		return fechaStr;
	}

	public void setFechaStr(String fechaStr) {
		this.fechaStr = fechaStr;
	}

	public List<LineaAlbaranSalida> getLineas() {
		return lineas;
	}

	public void setLineas(List<LineaAlbaranSalida> lineas) {
		this.lineas = lineas;
	}
	
	/*****************************************************************/
}
