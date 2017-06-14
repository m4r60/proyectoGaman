package modelos;

public class Variedades {
	/**
	 * PROPIEDADES
	 */
	private String tipo;
	private double precioKg;
	private double pesoCaja; /*Tendr�a que ser double porque un tipo tendr�a cajas de 2kg y la otra de 2,5kg*/
	private double precioCaja;
	/**
	 * CONSTRUCTOR VAC�O
	 */
	public Variedades (){}

	/**
	 * CONSTRUCTOR CON PAR�METROS
	 * No har�a falta pero lo metemos por si en un futuro se quieren introducir m�s variedades.
	 * @param tipo
	 * @param precioKg
	 * @param pesoCaja
	 * @param precioCaja
	 */
	public Variedades (String tipo, double precioKg, double pesoCaja, double precioCaja){
		this.tipo = tipo;
		this.precioKg = precioKg;
		this.pesoCaja = pesoCaja;
		this.precioCaja = precioCaja;
	}
	/**
	 * GETTERS Y SETTERS
	 */

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public double getPrecioKg() {
		return precioKg;
	}

	public void setPrecioKg(double precioKg) {
		this.precioKg = precioKg;
	}

	public double getPesoCaja() {
		return pesoCaja;
	}

	public void setPesoCaja(double pesoCaja) {
		this.pesoCaja = pesoCaja;
	}

	public double getPrecioCaja() {
		return precioCaja;
	}

	public void setPrecioCaja(double precioCaja) {
		this.precioCaja = precioCaja;
	}
	
}
