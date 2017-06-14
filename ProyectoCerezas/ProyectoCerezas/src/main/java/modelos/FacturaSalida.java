package modelos;

import java.util.Date;

import Utils.DateUtils;

public class FacturaSalida {
	/**
	 * PROPIEDADES
	 */
	private int nFactura;
	private Date fecha;
	private int iva;
	private double precioNeto;
	private boolean anulacion;
	
	/**
	 * CONSTRUCTOR VACÍO
	 */
	public FacturaSalida(){}
	/**
	 * 	CONSTRUCTOR CON PARÁMETROS
	 * @param fecha
	 * @param nFactura
	 * @param iva
	 * @param precioNeto
	 */
	public FacturaSalida(int nFactura, Date fecha, int iva, double precioNeto, boolean anulacion){
		this.nFactura = nFactura;
		this.fecha=fecha;
		this.iva = iva;
		this.precioNeto = precioNeto;
		this.anulacion=anulacion;
	}
	/**
	 * GETTERS Y SETTERS
	 */


	public int getIva() {
		return iva;
	}

	public void setIva(int iva) {
		this.iva = iva;
	}

	public double getPrecioNeto() {
		return precioNeto;
	}

	public void setPrecioNeto(double precioNeto) {
		this.precioNeto = precioNeto;
	}

	public int getnFactura() {
		return nFactura;
	}
	public Date getFecha() {
		return fecha;
	}
	public String getStringFecha(){
		return DateUtils.formatearFecha(fecha);
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public void setnFactura (int nFactura) {
		this.nFactura = nFactura;
	}
	public boolean isAnulacion() {
		return anulacion;
	}
	public void setAnulacion(boolean anulacion) {
		this.anulacion = anulacion;
	}
	
}
