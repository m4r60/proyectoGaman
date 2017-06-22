package modelos;

import java.util.Date;
import java.util.List;

import Utils.DateUtils;



public class AlbaranEntrada extends Cliente {
	/**
	 * PROPIEDADES
	 */
	private int nAlbaran;
	private int nSocio;
	private Date fecha; /*Hacer un Utils para gestionar la fecha*/
	private int nFactura;

	/******************************************************************************/
	/*Meto estas propiedades para poder mostrarlas dentro de albaranes Entrada*/
	private String cifNif;
	private double precioNetoE;
	private String fechaStr;
	private List<LineaAlbaranEntrada> lineas;
	/******************************************************************************/
	
	/**
	 * CONSTRUCTORES
	 */
	public AlbaranEntrada (){}
	/**
	 * CONSTRUCTOR PARA EL FORMULARIO
	 * @param nSocio
	 * @param fecha
	 * @param nFactura
	 */
	public AlbaranEntrada (int nSocio, Date fecha, int nFactura){
		this.nSocio = nSocio;
		this.fecha = fecha;
		this.nFactura = nFactura;	
	}
	/**
	 * CONSTRUCTOR PARA EL ROWMAPPER
	 * @param nAlbaran
	 * @param nSocio
	 * @param fecha
	 * @param nFactura
	 */
	public AlbaranEntrada (int nAlbaran,int nSocio, Date fecha, int nFactura){
		this.nAlbaran = nAlbaran;
		this.nSocio = nSocio;
		this.fecha = fecha;
		this.nFactura = nFactura;	
	}
/*Constructor listar Albaranes de Entrada*/
	
	public AlbaranEntrada (int nAlbaran,  int nSocio, Date fecha, int nFactura,  String cifNif){
		this.nSocio=nSocio;
		this.nAlbaran = nAlbaran;
		this.fecha = fecha;
		this.nFactura = nFactura;
		this.cifNif = cifNif;
	}
	
	/**
	 * GETTER AND SETTER
	 */
	public String getCifNif() {
		return cifNif;
	}
	public void setCifNif(String cifNif) {
		this.cifNif = cifNif;
	}
	public double getPrecioNetoE() {
		return precioNetoE;
	}
	public void setPrecioNetoE(double precioNetoE) {
		this.precioNetoE = precioNetoE;
	}
	public String getFechaStr() {
		return fechaStr;
	}
	public void setFechaStr(String fechaStr) {
		this.fechaStr = fechaStr;
	}
	
	public int getnSocio() {
		return nSocio;
	}
	public void setnSocio(int nSocio) {
		this.nSocio = nSocio;
	}
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
	public List<LineaAlbaranEntrada> getLineas() {
		return lineas;
	}
	public void setLineas(List<LineaAlbaranEntrada> lineas) {
		this.lineas = lineas;
	}
	
	
}
