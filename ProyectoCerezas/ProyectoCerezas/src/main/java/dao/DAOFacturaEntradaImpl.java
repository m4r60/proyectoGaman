package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import com.mysql.jdbc.Statement;
import org.springframework.jdbc.core.PreparedStatementCreator;
import modelos.FacturaEntrada;

public class DAOFacturaEntradaImpl implements DAOFacturaEntrada {

	class FacturaEntradaRowMapper implements RowMapper<FacturaEntrada> {

		public FacturaEntrada mapRow(ResultSet rs, int numRow) throws SQLException {
			FacturaEntrada fe = new FacturaEntrada(
					rs.getInt("n_factura"),
					new java.util.Date(rs.getDate("fecha").getTime()), 
					rs.getInt("iva"),
					rs.getDouble("precio_neto"),
					rs.getBoolean("anulacion"));

			return fe;
		}

	}

	/**
	 * Recupero la conexión con la base de datos.
	 */
	private DataSource dataSource;

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	/**
	 * Creo una conexion con DAOAlbaranEntrada
	 */
	private DAOAlbaranEntrada daoAlbaran;
	
	

	public DAOAlbaranEntrada getDaoAlbaran() {
		return daoAlbaran;
	}

	public void setDaoAlbaran(DAOAlbaranEntrada daoAlbaran) {
		this.daoAlbaran = daoAlbaran;
	}
	
	/**
	 * Función para crear un objeto FacturaEntrada, que devuelve el nFactura del objeto creado.
	 * @param fe -- Objeto FacturaEntrada
	 * @return nFactura
	 */
	public boolean create(final FacturaEntrada fe) {
		
		JdbcTemplate jdbc = new JdbcTemplate(dataSource);

		final String sql = "insert into factura_e (fecha, iva, precio_neto) values (?,?,?)";

		GeneratedKeyHolder kh = new GeneratedKeyHolder();
		final java.sql.Date d = new java.sql.Date(fe.getFecha().getTime());
		int n = jdbc.update(new PreparedStatementCreator() {

			public java.sql.PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				statement.setDate(1, d);
				statement.setInt(2, fe.getIva());
				statement.setDouble(3, fe.getPrecioNeto());
				return statement;
			}
		}, kh);
		
		
		fe.setnFactura(kh.getKey().intValue());
		return n>0;
	}

	public FacturaEntrada read(int nFactura) {
		FacturaEntrada fe = null;
		
		JdbcTemplate jdbc=new JdbcTemplate(dataSource);
		String sql="select * from factura_e where n_factura=?";
		
		try{
			fe=jdbc.queryForObject(sql,new Object[]{nFactura},new FacturaEntradaRowMapper());
		}
		catch(IncorrectResultSizeDataAccessException ics){
			System.out.println("Read FacturaEntrada -- Data access exception thrown when a result was not of the expected size, for example when expecting a single row but getting 0 or more than 1 rows.");
		}
		catch(DataAccessException dae){
			dae.printStackTrace();
			System.out.println("Read FacturaEntrada -- Error acceso de datos");
		} 
		
		return fe;
	}
	/*****************************************************************************************************************************/
	
	/**
	 * Función que modifica el objeto FacturaEntrada siempre que no esté anulada. 
	 * @param fe -- Se introduce un FacturaEntrada
	 * @return r -- Devuelve un boolean que determina si la función se ha ejecutado correctamente o no.
	 */
	public boolean update(FacturaEntrada fe) {
		boolean r=false;
		
		String sql="update factura_e set "
					+ "fecha=?, "	
					+ "iva=?, "
					+ "precio_neto=? "
				+ "where n_factura=? and anulacion=0";
		
		JdbcTemplate jdbc=new JdbcTemplate(dataSource);
		
		try{
			int n=jdbc.update(
					sql,
					new Object[]{
							new java.sql.Date(fe.getFecha().getTime()),
							fe.getIva(),
							fe.getPrecioNeto(),
							fe.getnFactura()});
			r=n>0;
		}
		catch(DataAccessException dae){
			dae.printStackTrace();
			System.out.println("Update FacturaEntrada -- Error acceso de datos");
			
		}
		
		return r;
	}
	
	/**
	 * Creamos una función que devuelve una lista con todos las facturas de entrada 
	 * no anuladas.
	 * @return lista
	 */
	public List<FacturaEntrada> listar() {
		List<FacturaEntrada> lista = null;
		
		JdbcTemplate jdbc=new JdbcTemplate(dataSource);
		String sql="select * from factura_e where anulacion=0 order by fecha desc";
		lista=jdbc.query(sql,new FacturaEntradaRowMapper());
		return lista;
	}
	
	/**
	 * Función sobrecargada que devuelve una lista con todos las facturas de entrada.
	 * anuladas y no anuladas
	 * Sql by laura y marco
	 * @param cifNif
	 * @return lista
	 */
	public List<FacturaEntrada> listar(String cifNif) {
		List<FacturaEntrada> lista;
		
		JdbcTemplate jdbc=new JdbcTemplate(dataSource);
		String sql="SELECT factura_e.precio_neto,"
				+ "factura_e.iva, "
				+ "factura_e.n_factura,"
				+ "factura_e.fecha,"
				+ "factura_e.anulacion "
				+ "from factura_e "
					+ "join albaranes_entrada on(factura_e.n_factura=albaranes_entrada.n_factura) "
					+ "join agricultores on (agricultores.n_socio=albaranes_entrada.n_socio) "
					+ "join personas on (agricultores.id_persona=personas.id_persona)"
				+ " WHERE personas.cif_nif =? order by fecha asc ";
		lista=jdbc.query(sql,new Object[]{cifNif}, new FacturaEntradaRowMapper());
		return lista;
	}
	
	public List<FacturaEntrada> listar(int nFactura){
		List<FacturaEntrada> lista = null;
		
		JdbcTemplate jdbc=new JdbcTemplate(dataSource);
		String sql="select * from factura_e where n_factura=?";
		
		try{
			lista=jdbc.query(sql,new Object[]{nFactura},new FacturaEntradaRowMapper());
		}
		catch(IncorrectResultSizeDataAccessException ics){
			System.out.println("listar FacturaEntrada -- Data access exception thrown when a result was not of the expected size, for example when expecting a single row but getting 0 or more than 1 rows.");
		}
		catch(DataAccessException dae){
			dae.printStackTrace();
			System.out.println("listar FacturaEntrada -- Error acceso de datos");
		}
		return lista;
	}
	
/*
	public List<FacturaEntrada> listar(Date fecha) {
		
		List<FacturaEntrada> lista;
		
		JdbcTemplate jdbc=new JdbcTemplate(dataSource);
		String sql="select * from factura_e where fecha=?";
		java.sql.Date d=new java.sql.Date(fecha.getTime());
		lista=jdbc.query(sql,new Object[]{d},new FacturaEntradaRowMapper());
		return lista;
	}
	*/
		
		
	/**
	 * Función para acotar la fecha de busqueda
	 * anuladas y no anuladas
	 * @param fechaInicio
	 * @param fechaFinal
	 * @return lista
	 */	
	
	public List<FacturaEntrada> buscarFecha (Date fechaInicio, Date fechaFinal){
		
		List<FacturaEntrada> lista;
		JdbcTemplate jdbc=new JdbcTemplate(dataSource);
		String sql="select * from factura_e where fecha BETWEEN ? AND ? order by fecha desc;";
		java.sql.Date fi=new java.sql.Date(fechaInicio.getTime());
		java.sql.Date ff=new java.sql.Date(fechaFinal.getTime());
		lista=jdbc.query(sql,new Object[]{fi,ff},new FacturaEntradaRowMapper());
		return lista;
	}
	
	/**
	 * Metodo anularFactrura, recibe una factura y pone anulacion a true.
	 * @param FactruraEntrada fe
	 * @return true o false
	 */
	
	public boolean anularFactura(int nFactura){
		boolean r=false;
		
		String sql="update factura_e set "
					+ "anulacion=1 "
				+ "where n_factura=?";
		
		JdbcTemplate jdbc=new JdbcTemplate(dataSource);
		
		try{
			int n=jdbc.update(
					sql,
					new Object[]{nFactura});
							
			r=n>0;
		}
		catch(DataAccessException dae){
			dae.printStackTrace();
			System.out.println("Baja - Error acceso de datos");
		}
		
		return r;
				
	}
	
	
	/**
	 * Metodo borrar factura, Borra una factura y pone a null nFactura todos los albaranes.
	 * OJO: Se usa sólo para los JUnit. Las facturas no se pueden borrar, solo modificar el estado
	 */
	
	public boolean delete(int nFactura) {
		
		String sql="delete from factura_e where n_factura=?";
		
		JdbcTemplate jdbc=new JdbcTemplate(dataSource);
		
		int n=jdbc.update(sql,new Object[]{nFactura});

		return n>0;
	}
	
	public double calcularPrecioFactura(int nAlbaran){
		double precio = 0;
		
		String sql="select sum(lineas_albaranes_e.precio_kg*lineas_albaranes_e.peso) "
				+ "from lineas_albaranes_e join albaranes_entrada "
				+ "on (lineas_albaranes_e.n_albaran=albaranes_entrada.n_albaran) "
				+ "where albaranes_entrada.n_factura=?";
		JdbcTemplate jdbc=new JdbcTemplate(dataSource);
		try{	
			precio=jdbc.queryForObject(sql,new Object[]{nAlbaran},Double.class);
		}
		catch(IncorrectResultSizeDataAccessException ics){
			System.out.println("CalcularPrecioFactura FacturaEntrada -- Data access exception thrown when a result was not of the expected size, for example when expecting a single row but getting 0 or more than 1 rows.");
		}
		catch(DataAccessException dae){
			dae.printStackTrace();
			System.out.println("CalcularPrecioFactura FacturaEntrada -- Error acceso de datos");
		} 
		
		return precio;
	}
	
	public FacturaEntrada readConDetalles(int nFactura){
		FacturaEntrada fe=read(nFactura);
		fe.setAlbaranes(daoAlbaran.listarConDetalle(nFactura));
		return fe;
	}
	
	public void meterDni(FacturaEntrada fe){
		String dni=null;
		System.out.println(fe.getnFactura());
		String sql="select DISTINCT personas.cif_nif from personas "
				+ "join agricultores on (agricultores.id_persona=personas.id_persona) "
				+ "join albaranes_entrada on (agricultores.n_socio=albaranes_entrada.n_socio) "
				+ "join factura_e on (factura_e.n_factura=albaranes_entrada.n_factura) "
			+ " WHERE factura_e.n_factura =? ";
		
		JdbcTemplate jdbc=new JdbcTemplate(dataSource);
		
		try{
			dni=jdbc.queryForObject(sql,new Object[]{fe.getnFactura()},String.class);
		}
		catch(IncorrectResultSizeDataAccessException ics){
			System.out.println("meterDni FacturaEntrada -- Data access exception thrown when a result was not of the expected size, for example when expecting a single row but getting 0 or more than 1 rows.");
		}
		catch(DataAccessException dae){
			dae.printStackTrace();
			System.out.println("meterDni FacturaEntrada -- Error acceso de datos");
		} 
		
		fe.setCifnif(dni);
	}
}
