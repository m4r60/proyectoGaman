package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import modelos.AlbaranSalida;



public class DAOAlbaranSalidaImpl implements DAOAlbaranSalida{
class AlbaranSalidaRowMapper implements RowMapper<AlbaranSalida>{
		
		public AlbaranSalida mapRow(ResultSet rs,int numRow) throws SQLException{
			AlbaranSalida as=new AlbaranSalida(
					rs.getInt("n_albaran"),
					rs.getInt("n_cliente"),
					new java.util.Date(rs.getDate("fecha").getTime()),
					rs.getInt("n_factura"));
			
			return as;
		}
		
	}

	/**
	 * Creamos una conexion con DAOLineasAlbaranSalida
	 */

	private DAOLineaAlbaranSalida daoLineas;



	public DAOLineaAlbaranSalida getDaoLineas() {
		return daoLineas;
	}

	public void setDaoLineas(DAOLineaAlbaranSalida daoLineas) {
		this.daoLineas = daoLineas;
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
	 * Función para crear un objeto AlbaranSalida, que devuelve el nAlbaran del objeto creado.
	 * @param a
	 * @return nAlbaran
	 */
	public boolean create(final AlbaranSalida a){
		JdbcTemplate jdbc=new JdbcTemplate(dataSource);
		
		final String sql="insert into albaranes_salida (n_cliente, fecha) values (?,?)";
		
		GeneratedKeyHolder kh=new GeneratedKeyHolder();
		final java.sql.Date d = new java.sql.Date(a.getFecha().getTime());
		
		int n=jdbc.update(new PreparedStatementCreator(){

			public java.sql.PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement statement =con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
				statement.setInt(1,a.getnCliente());
				statement.setDate(2, d);

				return statement;
			}
				
		},kh
		);
		
		a.setnAlbaran(kh.getKey().intValue());
		return n>0;		
	}
	
	
	/**
	 * Función que devuelve un objeto AlbaranSalida 
	 * @param nAlbaran
	 * @return as
	 */
	public AlbaranSalida read(int nAlbaran){
		AlbaranSalida as=null;
		
		JdbcTemplate jdbc=new JdbcTemplate(dataSource);
		
		String sql="select * from albaranes_salida where n_albaran=?";
		try{
			as=jdbc.queryForObject(sql,new Object[]{nAlbaran},new AlbaranSalidaRowMapper());
		}
		catch(IncorrectResultSizeDataAccessException ics){
			System.out.println("Read AlbaranSalida -- Data access exception thrown when a result was not of the expected size, for example when expecting a single row but getting 0 or more than 1 rows.");
		}
		catch(DataAccessException dae){
			dae.printStackTrace();
			System.out.println("Read AlbaranSalida -- Error acceso de datos");
		}
		
		return as;
	}
	
	/**
	 * Función que modifica un objeto AlbaranSalida
	 * @param as
	 * @return r -- Comprueba si se ha ejecutado bien la función.
	 */
	public boolean update(AlbaranSalida as){ //Modifica un albaran
		boolean r=false;
		
		String sql="update albaranes_salida set "
					+ "n_cliente=?, "
					+ "fecha=? "
				+ "where n_albaran=? and n_factura is null";
		
		JdbcTemplate jdbc=new JdbcTemplate(dataSource);
		
		try{
			int n=jdbc.update(
					sql,
					new Object[]{
							as.getnCliente(),
							new java.sql.Date(as.getFecha().getTime()),
							as.getnAlbaran()});
			r=n>0;
		}
		catch(DataAccessException dae){
			dae.printStackTrace();
			System.out.println("Update AlbaranEntrada -- Error acceso de datos");
		}
		
		return r;
	}
	
	/**
	 * Modifica el nFactura para indicar así que el albarán ha sido facturado
		si queremos quitar un albaran de una factura pasar nFactura=0 y el albarán 
		volvería a quedar sin estar facturado
	 * @param nAlbaran
	 * @param nFactura
	 * @return boolean -- Determina si la función se ha ejecutado correctamente o no.
	 */
	public boolean facturar(int nAlbaran, int nFactura){ 

		boolean r=false;
		
		if(nFactura>0){
			
			String sql="update albaranes_salida set n_factura=? where n_albaran=?";
		
			JdbcTemplate jdbc=new JdbcTemplate(dataSource);
		
			try{
				int n=jdbc.update(
					sql,
					new Object[]{nFactura, nAlbaran});
				r=n>0;
			}
			catch(DataAccessException dae){
				System.out.println("facturar AlbaranSalida - Error acceso de datos");
				dae.printStackTrace();
			}
		}
		
		else{
			
			String sql="update albaranes_salida set n_factura=null where n_albaran=?";
			
			JdbcTemplate jdbc=new JdbcTemplate(dataSource);
		
			try{
				int n=jdbc.update(
					sql,
					new Object[]{nAlbaran});
				r=n>0;
			}
			catch(DataAccessException dae){
				System.out.println("desfacturar AlbaranSalida - Error acceso de datos");
				dae.printStackTrace();
			}
		}
				
		return r;
	}
	/**
	 * Creamos una función que duelve una lista con todos los albaranes de salida.
	 * no facturados
	 * @return lista
	 */
	public List<AlbaranSalida> listar(){
		List<AlbaranSalida> lista;
		
		JdbcTemplate jdbc=new JdbcTemplate(dataSource);
		String sql="select * from albaranes_salida where n_factura is NULL order by fecha desc";
		lista=jdbc.query(sql,new AlbaranSalidaRowMapper());
		return lista;
	}
	/********************************************************************************************/
	/*Dentro del select hemos puesto los campos de AlbaranSalida habría que discutir que campos vamos a mostrar*/
	/**
	 * Creamos una función que duelve una lista
	 *  con todos los albaranes de entrada filtrados por cif_nif.
	 * @return lista
	 */
	public List<AlbaranSalida> listar(String cifNif){ 
		List<AlbaranSalida> lista;
		
		JdbcTemplate jdbc=new JdbcTemplate(dataSource);
		String sql="SELECT albaranes_salida.n_albaran,albaranes_salida.n_cliente,"
				+ "albaranes_salida.fecha,albaranes_salida.n_factura "
				+ "from albaranes_salida"
				+ " join clientes on (albaranes_salida.n_cliente = clientes.n_cliente) join"
				+ " personas on (personas.id_persona = clientes.id_persona)"
				+ " where personas.cif_nif = ? order by albaranes_salida.fecha desc ";
		lista=jdbc.query(sql,new Object[]{cifNif},new AlbaranSalidaRowMapper());
		return lista;
	}

	/**
	 * Función sobrecargada para buscar por fecha.
	 * @param fecha
	 * @return  lista -- Devuelve una lista con todos los objetos AlbaranEntrada
	 */
	public List<AlbaranSalida> listar(Date fecha){
		List<AlbaranSalida> lista;
		
		JdbcTemplate jdbc=new JdbcTemplate(dataSource);
		String sql="select * from albaranes_salida where fecha=?";
		java.sql.Date d=new java.sql.Date(fecha.getTime());
		lista=jdbc.query(sql,new Object[]{d},new AlbaranSalidaRowMapper());
		return lista;
	}
	
	/**
	 * funcion para acotar la fecha de busqueda
	 * @param fechaInicio
	 * @param fechaFinal
	 * @return lista
	 */
	public List<AlbaranSalida> buscarFecha (Date fechaInicio, Date fechaFinal){
		
		List<AlbaranSalida> lista;
		JdbcTemplate jdbc=new JdbcTemplate(dataSource);
		String sql="select * from albaranes_salida where fecha BETWEEN ? AND ? order by fecha desc;";
		java.sql.Date fi=new java.sql.Date(fechaInicio.getTime());
		java.sql.Date ff=new java.sql.Date(fechaFinal.getTime());
		lista=jdbc.query(sql,new Object[]{fi,ff},new AlbaranSalidaRowMapper());
		return lista;
	}
	/*****************************************************************************************************************/
	/*Hay que testear este método
	 * Es nuevo -- No hace falta porque buscamos por nAlbaran*/
	/*public List<AlbaranSalida> listar(int nFactura){
		List<AlbaranSalida> lista;
		
		JdbcTemplate jdbc=new JdbcTemplate(dataSource);
		String sql="select albaranes_salida.n_albaran,albaranes_salida.n_cliente,"
				+ "albaranes_salida.fecha,albaranes_salida.n_factura from albaranes_salida "
				+ "join clientes on (albaranes_salida.n_cliente = clientes.n_cliente) "
				+ "join personas on (personas.id_persona = clientes.id_persona) where albaranes_salida.n_factura=?";
		lista=jdbc.query(sql, new Object[]{nFactura},new AlbaranSalidaRowMapper());
		return lista;
	}*/
	
	public List <AlbaranSalida> listar (int nAlbaran){
		List<AlbaranSalida> lista;
		JdbcTemplate jdbc=new JdbcTemplate(dataSource);
		String sql="select n_albaran,n_cliente,"
				+ "fecha,n_factura "
				+ "from albaranes_salida"
				+ " where n_albaran=?";
		lista = jdbc.query (sql, new Object []{nAlbaran}, new AlbaranSalidaRowMapper());
		return lista;
	}
	/************************************************************************************************************/
	/*Dentro del select hemos puesto los campos de AlbaranSalida habría que discutir que campos vamos a mostrar*/
	/**
	 * Función sobrecargada que devuelve una lista con todos los albaranes no facturados.
	 * @param cifNif
	 * @return lista
	 */
	public List<AlbaranSalida> listarPendientes(String cifNif){ 
		
		List<AlbaranSalida> lista;
		
		JdbcTemplate jdbc=new JdbcTemplate(dataSource);
		String sql="SELECT albaranes_salida.n_albaran,albaranes_salida.n_cliente,"
				+ "albaranes_salida.fecha,albaranes_salida.n_factura from albaranes_salida "
				+ "join clientes on (albaranes_salida.n_cliente = clientes.n_cliente) "
				+ "join personas on (personas.id_persona = clientes.id_persona) "
				+ "where personas.cif_nif = ? and albaranes_salida.n_factura is NULL;";
		lista=jdbc.query(sql,new Object[]{cifNif},new AlbaranSalidaRowMapper());
		return lista;
	}
	/**
	 * Borra un albaran, hay que controlar que sea un albarán que nFactura sea nulo, es decir, que no esté facturado.
	 * @param nAlbaran
	 * @return boolean -- Devuelve un boolean que comprueba si se ha ejecutado correctamente o no.
	 */
	public boolean delete(int nAlbaran){ 
		String sql="delete from albaranes_salida where n_albaran=? and n_factura is NULL";
		
		JdbcTemplate jdbc=new JdbcTemplate(dataSource);
		
		int n=jdbc.update(sql,new Object[]{nAlbaran});
		
		return n>0;
	}
	
	/**
	 * Calcula el precio total, teníendo el iva y el precio neto.
	 * @param nAlbaran
	 * @return
	 */
	public double calcularPrecio(int nAlbaran){
		double precio = 0;
		
		String sql="select if(sum(lineas_albaranes_s.precio_caja*lineas_albaranes_s.numero_cajas) "
				+ "is null,0,sum(lineas_albaranes_s.precio_caja*lineas_albaranes_s.numero_cajas)) "
				+ "from lineas_albaranes_s where lineas_albaranes_s.n_albaran=?";
		JdbcTemplate jdbc=new JdbcTemplate(dataSource);
		System.out.println(jdbc);
		precio=jdbc.queryForObject(sql,new Object[]{nAlbaran},Double.class);
		return precio;
	}
	
	/**
	 * Función nueva
	 * Hay que añadirla también en albarán entrada
	 * Calcula el precio total sólo de una línea de albaran
	 * 
	 */
	public double calcularPrecioUnaLinea (int idLinea){
		double precio = 0;
		
		String sql="select lineas_albaranes_s.precio_caja*lineas_albaranes_s.numero_cajas "
				+ "from lineas_albaranes_s where lineas_albaranes_s.id_linea=?";
		JdbcTemplate jdbc=new JdbcTemplate(dataSource);
		
		precio=jdbc.queryForObject(sql,new Object[]{idLinea},Double.class);
		
		return precio;
	}
	
	/**
	 * Función que lee y devuelve un objeto AlbaranSalida. Se busca por nAlbaran
	 * @param nAlbaran
	 * @return ae
	 */
	public AlbaranSalida readConDetalles(int nAlbaran){
		AlbaranSalida as=read(nAlbaran);
		
		as.setLineas(daoLineas.listar(nAlbaran));
		
		return as;
	}
	
	public List<AlbaranSalida> listarConDetalle(int nFactura){
		ArrayList<AlbaranSalida> l=new ArrayList<AlbaranSalida>();
		
		JdbcTemplate jdbc=new JdbcTemplate(dataSource);
		
		String sql="select n_albaran from albaranes_salida where n_factura=?";
		List<Integer> ln=jdbc.queryForList(sql,new Object[]{nFactura},Integer.class);
		for(Integer na:ln){
			AlbaranSalida alb=readConDetalles(na);
			l.add(alb);
		}
		
		return l;
	}
}
