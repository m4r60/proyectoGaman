package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import modelos.Variedades;

public class DAOVariedadesImpl implements DAOVariedades {
	
	/**
	 * 
	 * @author cerezas
	 *
	 */

	class VariedadesRowMapper implements RowMapper<Variedades>{
		
		public Variedades mapRow(ResultSet rs,int numRow) throws SQLException{
			Variedades v=new Variedades(
					rs.getString("tipo"),
					rs.getDouble("precio_kg"),
					rs.getDouble("peso_caja"),
					rs.getDouble("precio_caja"));
			
			return v;
		}
		
	}
	/**
	 * Establecemos la conexión con la base de datos.
	 */
	private DataSource dataSource;
	
	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	/**
	 * Crear una variedad nueva de cerezas, no ponemos resultado CRUD para dar la opcion a poder crecer y añadir nuevas varidades o eliminar las existentes
	 * @param v
	 * @return true o false
	 */
	
	public boolean create(Variedades v){
		
		JdbcTemplate jdbc=new JdbcTemplate(dataSource);
				
		String sql="insert into variedades (tipo,precio_kg,peso_caja,precio_caja)"
				+ "values (?,?,?,?)";

		int n = jdbc.update(
			sql, new Object[]{
					v.getTipo(),
					v.getPrecioKg(),
					v.getPesoCaja(),
					v.getPrecioCaja()});
					
		return n>0;		
	}
	
	/**
	 * Recibe un tipo y devuelve todos las datos que corresponden a este
	 * @param tipo
	 * @return Variedad v
	 */
	public Variedades read(String tipo){
		Variedades v=null;
		
		JdbcTemplate jdbc=new JdbcTemplate(dataSource);
		
		String sql="select * from variedades where tipo=?";
		try{
			v=jdbc.queryForObject(sql,new Object[]{tipo},new VariedadesRowMapper());
		}
		catch(IncorrectResultSizeDataAccessException ics){
			System.out.println("Read Variedades -- Data access exception thrown when a result was not of the expected size, for example when expecting a single row but getting 0 or more than 1 rows.");

		}
		catch(DataAccessException dae){
			dae.printStackTrace();
			System.out.println("Read Variedades -- Error acceso de datos");
		}
		
		return v;
	}
	
	/**
	 * Modifica una variedad de cereza
	 * @param v
	 * @return true o false
	 * 
	 */
	public boolean update(Variedades v){ 
		boolean r=false;
		
		String sql="update variedades set " 
					+ "precio_kg=?,"
					+ "peso_caja=?,"
					+ "precio_caja=? "
				+ "where tipo=?";
		
		JdbcTemplate jdbc=new JdbcTemplate(dataSource);
		
		try{
			int n=jdbc.update(
					sql,
					new Object[]{
							v.getPrecioKg(),
							v.getPesoCaja(),
							v.getPrecioCaja(),
							v.getTipo()});
			r=n>0;
		}
		catch(DataAccessException dae){
			dae.printStackTrace();
			System.out.println("Update variedades - Error acceso de datos");
		}
		
		return r;
	}
	
	/**
	 * Listar todos los tipos de cerezas
	 * @return lista de variedades
	 * 
	 */
	public List<Variedades> listar(){ 
		List<Variedades> lista;
		
		JdbcTemplate jdbc=new JdbcTemplate(dataSource);
		String sql="select * from variedades order by tipo desc";
		lista=jdbc.query(sql,new VariedadesRowMapper());
		return lista;
	}
	
	/**
	 * FUNCIÓN NUEVA- NO ESTÁ TESTEADA 
	 * @return lista de variedades
	 * 
	 */
	public List<Variedades> listar(String busqueda) {
		List<Variedades> lista = null;

		JdbcTemplate jdbc = new JdbcTemplate(dataSource);

		String sql = "select * from variedades where tipo like ?";
		try {
			String b="%"+busqueda+"%";
			//System.out.println(b);
			lista = jdbc.query(sql, new Object[] {b}, new VariedadesRowMapper());
		} catch (IncorrectResultSizeDataAccessException ics) {
			System.out.println(
					"ArrayList <Cliente> read - Data access exception thrown when a result was not of the expected size, for example when expecting a single row but getting 0 or more than 1 rows.");
		} catch (DataAccessException dae) {
			dae.printStackTrace();
			System.out.println("ArrayList <Cliente> read - Error acceso de datos");
		}

		return lista;
	}
	
	
	/**
	 * Borra una variedad de cerezas y sus datos. 
	 * El borrado de una variedad no afecta a las 
	 * facturas o albaranes generados anteriormente, 
	 * ya que el borrado no es en cascada
	 * @param nAlbaran
	 * @return boolean -- Comprobar si la función se ha llevado a cabo correctamente
	 */
	
	public boolean delete(String tipo){ 
		
		String sql="delete from variedades where tipo=?";
		
		JdbcTemplate jdbc=new JdbcTemplate(dataSource);
		
		int n=jdbc.update(sql,new Object[]{tipo});

		return n>0;
	}
	
}
