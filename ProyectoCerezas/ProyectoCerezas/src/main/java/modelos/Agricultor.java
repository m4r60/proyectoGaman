package modelos;

public class Agricultor extends Persona {
	/**
	 * PROPIEDADES
	 */
	private int nSocio;
	private boolean baja;
	
	/**
	 * CONSTRUCTORES
	 */
	public Agricultor (){}
	/*Este constructor es para el formulario*/
	public Agricultor (String cifNif, String nombreRazonSocial, String apellidos, 
			String direccion, String telefono, String email){
		super (cifNif, nombreRazonSocial, apellidos, direccion, telefono, email);
		this.baja = false;
	}
	
	/*Este constructor lo usamos para recuperar clientes en el RowMapper*/
	public Agricultor (int idPersona,String cifNif, String nombreRazonSocial, String apellidos, 
			String direccion, String telefono, String email, int nSocio, boolean baja){
		super (idPersona,cifNif, nombreRazonSocial, apellidos, direccion, telefono, email);
		this.nSocio = nSocio;
		this.baja = baja;
	}
	public Agricultor(int nSocio){
		super(nSocio);
	}
	public Agricultor (int idPersona, boolean baja){
		super(idPersona);
		this.baja=baja;
	}
	/**
	 * GETTERS AND SETTERS
	 */
	public int getnSocio() {
		return nSocio;
	}
	public void setnSocio(int nSocio) {
		this.nSocio = nSocio;
	}
	public boolean isBaja() {
		return baja;
	}
	public void setBaja(boolean baja) {
		this.baja = baja;
	}
	
	
}
