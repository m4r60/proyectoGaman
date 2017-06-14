package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import modelos.Agricultor;

public class DAOAgricultorImpl implements DAOAgricultor {
	
	
class AgricultorRowMapper implements RowMapper<Agricultor>{
		
		public Agricultor mapRow(ResultSet rs,int numRow) throws SQLException{
			
			Agricultor c=new Agricultor(
				rs.getInt("id_persona"),
				rs.getString("cif_nif"),
				rs.getString("nombre_razon_social"),
				rs.getString("apellidos"),
				rs.getString("direccion"),
				rs.getString("telefono"),
				rs.getString("email"),
				rs.getInt("n_socio"),
				rs.getBoolean("baja"));
				
			return c;
		}
	}
	
/**
 * ESTABLECEMOS LA CONEXIÓN CON LA BASE DE DATOS
 */
	private DataSource dataSource;
	
	public DataSource getDataSource(){
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	/**
	 * MÉTODO CREACIÓN DE UN OBJETO DE TIPO AGRICULTOR
	 * 
	 * * ************Crear agricultor una vez comprobado ID_PERSONA
	 
	 * @param Agricultor a
	 * @return boolean que señala si se ha ejecutado bien el método o no.
	 */
	public boolean create(final Agricultor a){
		
		JdbcTemplate jdbc=new JdbcTemplate(dataSource);
		
		//System.out.println(a.getIdPersona());
		GeneratedKeyHolder kh=new GeneratedKeyHolder();
		final String sql = "insert into agricultores (id_persona, baja) values (?,?)";
		int n = jdbc.update(new PreparedStatementCreator(){

			public java.sql.PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement statement =con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
				statement.setInt(1, a.getIdPersona());
				statement.setBoolean(2, a.isBaja());
				return statement;
			}
				
		},kh);
		a.setnSocio(kh.getKey().intValue());
		return n>0;		
	}

	/**
	 * Función que recupera un objeto agricultor por su idPersona, 
	 * para saber si una persona es agricultor o no.
	 *
	 * **************Comprueba si el AGRICULTOR existe 
	 * 
	 * 
	 * @param idPersona
	 * @return Agricultor c
	 */
	public Agricultor read(int idPersona){ 
		Agricultor c=null;
		
		JdbcTemplate jdbc=new JdbcTemplate(dataSource);
		
		String sql="select personas.*, agricultores.n_socio, agricultores.baja from personas "
				+ "join agricultores ON (agricultores.id_persona=personas.id_persona)"
				+ " where agricultores.id_persona=?";
		try{
			c=jdbc.queryForObject(sql,new Object[]{idPersona},new AgricultorRowMapper()); /*Tenemos que tratar esto con AOP*/
		}
		catch(IncorrectResultSizeDataAccessException ics){
			System.out.println("Read Agricultor - Data access exception thrown when a result was not of the expected size, for example when expecting a single row but getting 0 or more than 1 rows.");
		}
		catch(DataAccessException dae){
			dae.printStackTrace();
			System.out.println("Read Agricultor - Error acceso de datos");
		}
		
		return c;
	}
	
	
	/**
	 * Función que recupera un objeto cliente por su nSocio, 
	 * para saber si una persona es cliente o no.
	 * 
	 ********** READ para boton modificar agricultor ya existente	 * 
	 * 
	 * @param nSocio
	 * @return Agricultor c
	 */
	public Agricultor readModificar(int nSocio){ 
		Agricultor c=null;
		
		JdbcTemplate jdbc=new JdbcTemplate(dataSource);
		
		String sql="select personas.*, agricultores.n_socio, agricultores.baja from personas "
				+ "join agricultores ON (agricultores.id_persona=personas.id_persona)"
				+ " where agricultores.n_socio=?";
		try{
			c=jdbc.queryForObject(sql,new Object[]{nSocio},new AgricultorRowMapper()); /*Tenemos que tratar esto con AOP*/
		}
		catch(IncorrectResultSizeDataAccessException ics){
			System.out.println("Read Agricultor - Data access exception thrown when a result was not of the expected size, for example when expecting a single row but getting 0 or more than 1 rows.");
		}
		catch(DataAccessException dae){
			dae.printStackTrace();
			System.out.println("Read Agricultor - Error acceso de datos");
		}
		
		return c;
	}
	
	/**
	 * Busca agricultores por nombre, apellido NIE o nº telefono tanto en estado alta como baja
	 * 
	  * ******************LISTAR sin restricción para el filtro de listadoCliente
	 *
	 * 
	 * @param String busqueda
	 * @return Lista de agricultores donde haya coincidencias
	 */
	public List<Agricultor> listar(String busqueda){ 

		List<Agricultor> lista=null;
		
		JdbcTemplate jdbc=new JdbcTemplate(dataSource);
		
		String sql="select personas.*, agricultores.n_socio, agricultores.baja "
				+ "from personas join agricultores on (personas.id_persona=agricultores.id_persona) "
				+ "where cif_nif like ? or nombre_razon_social like ? or apellidos like ? or telefono like ?";
		try{
			String b="%"+busqueda+"%";
			//System.out.println(b);
			lista=jdbc.query(sql,new Object[]{b,b,b,b},new AgricultorRowMapper());
		}
		catch(IncorrectResultSizeDataAccessException ics){
			System.out.println("ArrayList <Agricultor> read - Data access exception thrown when a result was not of the expected size, for example when expecting a single row but getting 0 or more than 1 rows.");
		}
		catch(DataAccessException dae){
			dae.printStackTrace();
			System.out.println("ArrayList <Agricultor> read - Error acceso de datos");
		}
				
		return lista;
	}

	/**
	 * Función que modifica el objeto agricultor. El agricultor se busca por id_persona.
	 * 
	 * ******************Update para botón modificar del la jsp "modificarCliente"
	 *
	 * @param c
	 * @return boolean -- Que determina si se ha llevado a cabo correctamente la función o no.
	 */
	public boolean update(Agricultor c){
		boolean r=false;
		
		String sql="update personas "
				+ "join agricultores on (personas.id_persona=agricultores.id_persona) set "
				+ "personas.cif_nif=?, "
				+ "personas.nombre_razon_social=?,"
				+ "personas.apellidos=?,"
				+ "personas.direccion=?,"
				+ "personas.telefono=?,"
				+ "personas.email=? "
			+ "where personas.id_persona=? and agricultores.baja=0";
		
		JdbcTemplate jdbc=new JdbcTemplate(dataSource);
		
		try{
			int n=jdbc.update(sql,new Object[]{
					c.getCifNif(),
					c.getNombreRazonSocial(),
					c.getApellidos(),
					c.getDireccion(),
					c.getTelefono(),
					c.getEmail(),
					c.getIdPersona()});
			r=n>0;
		}
		catch(DataAccessException dae){
			dae.printStackTrace();
			System.out.println("Update - Error acceso de datos");
		}
		
		return r;
	}
	
	/**
	 * Función que devuelve un List con todos los agricultores dados de alta
	 * 
	 **************LISTAR con restricción
	 *  
	 * @return lista
	 */
	public List<Agricultor> listar(){ 
		
		List<Agricultor> lista;
				
		JdbcTemplate jdbc=new JdbcTemplate(dataSource);
		String sql="select personas.*, agricultores.n_socio, agricultores.baja "
				+ "from personas join agricultores on (personas.id_persona=agricultores.id_persona) "
				+ "where agricultores.baja = 0 "
				+ "order by personas.nombre_razon_social";
		lista=jdbc.query(sql,new AgricultorRowMapper());

		return lista;
	}
	
	/**
	 * Creamos un método que modifica el campo baja de la tabla agricultores.
	 * @param Agricultor c
	 * @return r 
	 */
	public boolean baja (Agricultor c){
		boolean r=false;
		
		String sql="update agricultores set "
				+ "baja=?  "
				+ "where n_socio=?";
		
		JdbcTemplate jdbc=new JdbcTemplate(dataSource);
		
		try{
			int n=jdbc.update(sql, new Object[]{
					c.isBaja(),c.getnSocio()});		
			r=n>0;
		}
		catch(DataAccessException dae){
			dae.printStackTrace();
			System.out.println("Baja - Error acceso de datos");
		}
		
		return r;
	}
	
	/**
	 * Metodo para borrar un agricultor "OJO:Solo se usa para los JUnit"
	 * @param nSocio
	 * @return
	 */
	public boolean delete(int nSocio){ 
		
		String sql="delete from agricultores where n_socio=?";
		
		JdbcTemplate jdbc=new JdbcTemplate(dataSource);
		
		int n=jdbc.update(sql,new Object[]{nSocio});
		return n>0;
	}

	
	
}
