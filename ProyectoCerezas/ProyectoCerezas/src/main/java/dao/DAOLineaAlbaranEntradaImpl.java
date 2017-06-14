package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import modelos.LineaAlbaranEntrada;

public class DAOLineaAlbaranEntradaImpl implements DAOLineaAlbaranEntrada{
	
	/**
	 * Creamos una clase RowMapper para recuperar objetos de clase LineaAlbaranEntrada. 
	 */
	class LineaAlbaranEntradaRowMapper implements RowMapper<LineaAlbaranEntrada>{
		
		public LineaAlbaranEntrada mapRow(ResultSet rs,int numRow) throws SQLException{
			LineaAlbaranEntrada lae=new LineaAlbaranEntrada(
					rs.getInt("n_albaran"),
					rs.getInt("id_linea_e"),
					rs.getString("tipo"),
					rs.getDouble("peso"),
					rs.getDouble("precio_kg"));
			
			return lae;
		}
		
	}
	
	/**
	 * Conexión con la base de datos.
	 */
	private DataSource dataSource;
	
	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	/**
	 * Crear una nueva linea en un albaran
	 * @param LineaAlbaranEntrada
	 * @return true o false
	 */
	public boolean create(LineaAlbaranEntrada lae){
		
		JdbcTemplate jdbc=new JdbcTemplate(dataSource);
				
		String sql="insert into lineas_albaranes_e (n_albaran,tipo,peso,precio_kg)"
				+ "values (?,?,?,?)";

		int n = jdbc.update(
			sql, new Object[]{
					lae.getnAlbaran(),
					lae.getTipo(),
					lae.getPeso(),
					lae.getPrecioKg()});
					
		return n>0;		
	}
	
	/**
	 * Leer una linea por su id
	 * @param idLinea
	 * @return Linea Albaran
	 */
	public LineaAlbaranEntrada read(int idLinea){
		LineaAlbaranEntrada lae=null;
		
		JdbcTemplate jdbc=new JdbcTemplate(dataSource);
		
		String sql="select * from lineas_albaranes_e where id_linea_e=?";
		try{
			lae=jdbc.queryForObject(sql,new Object[]{idLinea},new LineaAlbaranEntradaRowMapper());
		}
		catch(IncorrectResultSizeDataAccessException ics){
			System.out.println("Read LineaAlbaranesEntrada -- Data access exception thrown when a result was not of the expected size, for example when expecting a single row but getting 0 or more than 1 rows.");

		}
		catch(DataAccessException dae){
			dae.printStackTrace();
			System.out.println("Read LineaAlbaranEntrada -- Error acceso de datos");
		}
		
		return lae;
	}
	
	/**
	 * Modifica una linea de albaran
	 * @param idLinea
	 * @return true o false
	 * 
	 */
	public boolean update(LineaAlbaranEntrada lae){ 
		boolean r=false;
		
		String sql="update lineas_albaranes_e set " 
					+ "n_albaran=?,"
					+ "tipo=?,"
					+ "peso=?,"
					+ "precio_kg=?"
				+ "where id_linea_e=?";
		
		JdbcTemplate jdbc=new JdbcTemplate(dataSource);
		
		try{
			int n=jdbc.update(
					sql,
					new Object[]{
							lae.getnAlbaran(),
							lae.getTipo(),
							lae.getPeso(),
							lae.getPrecioKg()});
			r=n>0;
		}
		catch(DataAccessException dae){
			dae.printStackTrace();
			System.out.println("update lineasAlbaranEntrada -- Data access exception thrown when a result was not of the expected size, for example when expecting a single row but getting 0 or more than 1 rows.");

		}
		
		return r;
	}
	
	/**
	 * Listar todos las lineas de albaran de un albaran
	 * @param nAlbaran
	 * @return lista de LineaAlbaranEntrada
	 * 
	 */
	public List<LineaAlbaranEntrada> listar(int nAlbaran){ 
		List<LineaAlbaranEntrada> lista;
		
		JdbcTemplate jdbc=new JdbcTemplate(dataSource);
		String sql="select * from lineas_albaranes_e where n_albaran=? order by id_linea_e desc";
		lista=jdbc.query(sql,new Object[]{nAlbaran},new LineaAlbaranEntradaRowMapper());
		return lista;
	}
	
	/**
	 * Borra una linea
	 * @param idLinea
	 * @return true o false
	 */
	
	public boolean delete(int idLinea){ 
		
		
		
		String sql="delete from lineas_albaranes_e where id_linea_e=?";
		
		JdbcTemplate jdbc=new JdbcTemplate(dataSource);
		
		int n=jdbc.update(sql,new Object[]{idLinea});
		return n>0;
	}
	

}
