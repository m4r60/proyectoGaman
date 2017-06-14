package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import modelos.Persona;

public class DAOPersonaImpl implements DAOPersona {
	
	class PersonaRowMapper implements RowMapper<Persona>{
		
		public Persona mapRow(ResultSet rs,int numRow) throws SQLException{
			
			Persona p=new Persona(
				rs.getInt("id_persona"),
				rs.getString("cif_nif"),
				rs.getString("nombre_razon_social"),
				rs.getString("apellidos"),
				rs.getString("direccion"),
				rs.getString("telefono"),
				rs.getString("email"));
			return p;
		}
	}
	
	private DataSource dataSource;

	public DataSource getDataSource(){
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public boolean create(final Persona p){ //Devuelve el ultimo id_persona de la tabla personas
		
		JdbcTemplate jdbc=new JdbcTemplate(dataSource);
		
		final String sql="insert into personas (cif_nif, nombre_razon_social, apellidos, direccion, telefono, email)"
				+ " values (?,?,?,?,?,?)";
		
		GeneratedKeyHolder kh=new GeneratedKeyHolder();
		int n = jdbc.update(new PreparedStatementCreator(){

			public java.sql.PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement statement =con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
				statement.setString(1, p.getCifNif());
				statement.setString(2, p.getNombreRazonSocial());
				statement.setString(3, p.getApellidos());
				statement.setString(4, p.getDireccion());
				statement.setString(5, p.getTelefono());
				statement.setString(6, p.getEmail());
				
				return statement;
			}
				
		},kh
		);
		p.setIdPersona(kh.getKey().intValue());
		return n>0;		
	}
	
	public Persona read(String cifNif){ //Busca Persona por su nie
		Persona p=null;
		
		JdbcTemplate jdbc=new JdbcTemplate(dataSource);
	
		String sql="select * from personas where cif_nif=?";
		try{
			
			p=jdbc.queryForObject(sql,new Object[]{cifNif},new PersonaRowMapper());
		}
		catch(IncorrectResultSizeDataAccessException ics){
			System.out.println("Read Personas - Data access exception thrown when a result was not of the expected size, for example when expecting a single row but getting 0 or more than 1 rows.");
			
		}
		catch(DataAccessException dae){
			dae.printStackTrace();
			System.out.println("Read Persona - Error acceso de datos");
			
		}

		return p;
	}
	/**
	 *  Función que lee persona por su id
	 *  Arti No lo toques!!!  Funciona
	 *  ****ListadoCliente - Lo usamos cuando en nuevo cliente, introducimos el DNI y le damos a enviar.
	 * Nos recupera la persona por su id, después de haber recuperado el id de cliente.
	 * @param idPersona
	 * @return Persona
	 */
	public Persona read (int idPersona){
		Persona p=null;
		
		JdbcTemplate jdbc=new JdbcTemplate(dataSource);
	
		String sql="select * from personas where id_persona=?";
		
		try{
			
			p=jdbc.queryForObject(sql,new Object[]{idPersona},new PersonaRowMapper());
		}
		catch(IncorrectResultSizeDataAccessException ics){
			System.out.println("Read Personas - Data access exception thrown when a result was not of the expected size, for example when expecting a single row but getting 0 or more than 1 rows.");
			
		}
		catch(DataAccessException dae){
			dae.printStackTrace();
			System.out.println("Read Persona - Error acceso de datos");
			
		}
		return p;
	}

	/**
	 * Función Nueva -- Hay que testearla.
	 * Que lo mire jose
	 * Función para recuperar el objeto persona desde albaranSalida.
	 * @param nCliente
	 * @return
	 */
	public Persona readAlbaranSalida (int nCliente){
		Persona p = null;
		JdbcTemplate jdbc=new JdbcTemplate(dataSource);
		
		String sql="select distinct personas.* from albaranes_salida "
				+ "join clientes on (clientes.n_cliente=albaranes_salida.n_cliente) "
				+ "join personas on (personas.id_persona=clientes.id_persona) "
				+ "where albaranes_salida.n_cliente=?";
	
		try{
			p=jdbc.queryForObject(sql,new Object[]{nCliente},new PersonaRowMapper());
			
		}
		catch(IncorrectResultSizeDataAccessException ics){
			System.out.println("Read AlbaranSalida Persona-- Data access exception thrown when a result was not of the expected size, for example when expecting a single row but getting 0 or more than 1 rows.");
		}
		catch(DataAccessException dae){
			dae.printStackTrace();
			System.out.println("Read AlbaranSalida Persona -- Error acceso de datos");
		}
		return p;
	}
	
	public Persona readAlbaranEntrada (int nSocio){
		Persona p = null;
		JdbcTemplate jdbc=new JdbcTemplate(dataSource);
		
		String sql="select distinct personas.* from albaranes_entrada "
				+ "join agricultores on (agricultores.n_socio=albaranes_entrada.n_socio) "
				+ "join personas on (personas.id_persona=agricultores.id_persona) "
				+ "where albaranes_entrada.n_socio=?";
	
		try{
			p=jdbc.queryForObject(sql,new Object[]{nSocio},new PersonaRowMapper());
			
		}
		catch(IncorrectResultSizeDataAccessException ics){
			System.out.println("Read AlbaranE Persona-- Data access exception thrown when a result was not of the expected size, for example when expecting a single row but getting 0 or more than 1 rows.");
		}
		catch(DataAccessException dae){
			dae.printStackTrace();
			System.out.println("Read AlbaranE Persona -- Error acceso de datos");
		}
		return p;
	}
	/**
	 * Metdodo para borrar una persona "OJO:Solo se usa en los JUnit"
	 * @param idPersona
	 * @return
	 */
	public boolean delete(int idPersona){ 
		
		String sql="delete from personas where id_persona=? ";
		
		JdbcTemplate jdbc=new JdbcTemplate(dataSource);
		
		int n=jdbc.update(sql,new Object[]{idPersona});

		return n>0;
	}
	
}
