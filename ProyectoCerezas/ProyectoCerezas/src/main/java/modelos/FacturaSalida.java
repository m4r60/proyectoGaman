package modelos;

import java.util.Date;
import java.util.List;

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
	private String cifnif;
	private Double precioTotal;
	
	public List<AlbaranSalida> getAlbaranes() {
		return albaranes;
	}
	public void setAlbaranes(List<AlbaranSalida> albaranes) {
		this.albaranes = albaranes;
	}
	private List<AlbaranSalida> albaranes;
	
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
	public String getCifnif() {
		return cifnif;
	}
	public void setCifnif(String cifnif) {
		this.cifnif = cifnif;
	}
	public Double getPrecioTotal() {
		return precioTotal;
	}
	public void setPrecioTotal(Double precioTotal) {
		this.precioTotal = precioTotal;
	}
	
	
}
