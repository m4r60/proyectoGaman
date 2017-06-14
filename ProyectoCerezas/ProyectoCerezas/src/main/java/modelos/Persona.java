package modelos;

public class Persona {
	
	/**
	 * PROPIEDADES
	 */
	private int idPersona;
	private String cifNif;
	private String nombreRazonSocial;
	private String apellidos;
	private String direccion;
	private String telefono;
	private String email;
	
	/**
	 * CONSTRUCTORES (No haría falta el constructor aquí ya que las clases hijas heredan de aquí y es donde se creara el cliente o el agricultor)
	 */
	public Persona(){}
	
	
	/**
	 * Constructor con parámetros para ser utilizado en el RowMapper del DAO
	 * @param idPersona
	 * @param cifNif
	 * @param nombreRazonSocial
	 * @param apellidos
	 * @param direccion
	 * @param telefono
	 * @param email
	 */
	public Persona (int idPersona,String cifNif, String nombreRazonSocial, String apellidos, 
			String direccion, String telefono, String email){
		this.idPersona = idPersona;
		this.cifNif = cifNif;
		this.nombreRazonSocial = nombreRazonSocial;
		this.apellidos = apellidos;
		this.direccion = direccion;
		this.telefono = telefono;
		this.email = email;
	}
	public Persona(int idPersona){
		this.idPersona = idPersona;
	}
	
	
	/**
	 * Constructor con parámetros para crear objetos de tipo persona con el formulario.
	 * @param cifNif
	 * @param nombreRazonSocial
	 * @param apellidos
	 * @param direccion
	 * @param telefono
	 * @param email
	 */
	public Persona(String cifNif, String nombreRazonSocial, String apellidos, 
			String direccion, String telefono, String email){
		this.cifNif = cifNif;
		this.nombreRazonSocial = nombreRazonSocial;
		this.apellidos = apellidos;
		this.direccion = direccion;
		this.telefono = telefono;
		this.email = email;
	}
	/**
	 * GETTERS Y SETTERS
	 *
	 */
	
	
	
	public String getCifNif() {
		return cifNif;
	}
	public void setCifNif(String cifNif) {
		this.cifNif = cifNif;
	}
	public String getNombreRazonSocial() {
		return nombreRazonSocial;
	}
	public void setNombreRazonSocial(String nombreRazonSocial) {
		this.nombreRazonSocial = nombreRazonSocial;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getIdPersona() {
		return idPersona;
	}
	
	public void setIdPersona(int idPersona) {
		this.idPersona = idPersona;
	}
	
	
}
