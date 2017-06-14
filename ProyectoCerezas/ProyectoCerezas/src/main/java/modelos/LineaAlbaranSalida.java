package modelos;

public class LineaAlbaranSalida { 
	/**
	 * PROPIEDADES
	 */
	private int nAlbaran;
	private int idLinea;
	private String tipo; 
	private int nCajas;
	private double pesoCaja; 
	private double precioCaja;
	private double precioTotal; /*Introduzco esta propiedad para incluirla dentro del objeto LineaAlbaranSalida
	Me hace falta para el controladorAlbaranSalida*/
	
	/**
	 * CONSTRUCTORES
	 */
	public LineaAlbaranSalida(){}

	/**
	 * CONSTRUCTOR PARA EL FORMULARIO
	 * @param tipo
	 * @param nCajas
	 * @param peso
	 * @param precioCaja
	 */
	public LineaAlbaranSalida(int nAlbaran,String tipo,int nCajas, double pesoCaja, double precioCaja){
		this.nAlbaran=nAlbaran;
		this.tipo = tipo;
		this.nCajas = nCajas;
		this.pesoCaja = pesoCaja;
		this.precioCaja = precioCaja;
		/*He cambiado constructor*/
	}
	
	/**
	 * CONSTRUCTOR PARA EL ROWMAPPER
	 * @param idLinea
	 * @param tipo
	 * @param nCajas
	 * @param pesoCaja
	 * @param precioCaja
	 * @param nAlbaran
	 */
	public LineaAlbaranSalida(int nAlbaran,int idLinea, String tipo, int nCajas, double pesoCaja, double precioCaja){
		this.nAlbaran = nAlbaran;
		this.idLinea = idLinea;
		this.tipo = tipo;
		this.nCajas = nCajas;
		this.pesoCaja = pesoCaja;
		this.precioCaja = precioCaja;
	}
	
	/**
	 * GETTER AND SETTER
	 */
	public String getTipo() {
		return tipo;
	}
	public double getPesoCaja() {
		return pesoCaja;
	}

	public void setPesoCaja(double pesoCaja) {
		this.pesoCaja = pesoCaja;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public int getIdLinea() {
		return idLinea;
	}
	public int getnCajas() {
		return nCajas;
	}
	public void setnCajas(int nCajas) {
		this.nCajas = nCajas;
	}
	public double getPrecioCaja() {
		return precioCaja;
	}
	public void setPrecioCaja(double precioCaja) {
		this.precioCaja = precioCaja;
	}
	public int getnAlbaran() {
		return nAlbaran;
	}

	public void setnAlbaran(int nAlbaran) {
		this.nAlbaran = nAlbaran;
	}

	public void setIdLinea(int idLinea) {
		this.idLinea = idLinea;
	}

	public double getPrecioTotal() {
		return precioTotal;
	}

	public void setPrecioTotal(double precioTotal) {
		this.precioTotal = precioTotal;
	}
	
	

}
