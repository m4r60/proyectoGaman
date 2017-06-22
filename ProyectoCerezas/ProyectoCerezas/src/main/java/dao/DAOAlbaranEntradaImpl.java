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

import modelos.AlbaranEntrada;



public class DAOAlbaranEntradaImpl implements DAOAlbaranEntrada{
	class AlbaranEntradaRowMapper implements RowMapper<AlbaranEntrada>{
		
		public AlbaranEntrada mapRow(ResultSet rs,int numRow) throws SQLException{
			AlbaranEntrada ae=new AlbaranEntrada(
					rs.getInt("n_albaran"),
					rs.getInt("n_socio"),
					new java.util.Date(rs.getDate("fecha").getTime()),
					rs.getInt("n_factura"));
			
			return ae;
		}
		
	}
	
	/**
	 * Creamos una conexion con DaoLineasAlbaranEntrada
	 */
	
	private DAOLineaAlbaranEntrada daoLineas;
	
	
	
	public DAOLineaAlbaranEntrada getDaoLineas() {
		return daoLineas;
	}

	public void setDaoLineas(DAOLineaAlbaranEntrada daoLineas) {
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
	 * Función para crear un objeto AlbaranEntrada, que devuelve el nAlbaran del objeto creado.
	 * @param a
	 * @return nAlbaran
	 */
public boolean create(final AlbaranEntrada a){
		JdbcTemplate jdbc=new JdbcTemplate(dataSource);
		
		final String sql="insert into albaranes_entrada (n_socio, fecha) values (?,?)";
		
		GeneratedKeyHolder kh=new GeneratedKeyHolder();
		final java.sql.Date d = new java.sql.Date(a.getFecha().getTime());
		
		int n= jdbc.update(new PreparedStatementCreator(){
			
			public java.sql.PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement statement =con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
				statement.setInt(1,a.getnSocio());
				statement.setDate(2, d);
				return statement;
			}
				
		},kh
		);
		a.setnAlbaran(kh.getKey().intValue());
		return n>0;		
	}
	
	
	/**
	 * Función que devuelve un objeto AlbaranEntrada
	 * @param nAlbaran
	 * @return ae
	 */
	public AlbaranEntrada read(int nAlbaran){
		AlbaranEntrada ae=null;
		
		JdbcTemplate jdbc=new JdbcTemplate(dataSource);
		
		String sql="select * from albaranes_entrada where n_albaran=?";
		try{
			ae=jdbc.queryForObject(sql,new Object[]{nAlbaran},new AlbaranEntradaRowMapper());
		}
		catch(IncorrectResultSizeDataAccessException ics){
			System.out.println("Read AlbaranEntrada -- Data access exception thrown when a result was not of the expected size, for example when expecting a single row but getting 0 or more than 1 rows.");
		}
		catch(DataAccessException dae){
			dae.printStackTrace();
			System.out.println("Read AlbaranEntrada -- Error acceso de datos");
		} 
		
		return ae;
	}
	
	/**
	 * Función que modifica el objeto AlbaranEntrada. 
	 * @param ae
	 * @return r -- Devuelve un boolean que determina si la función se ha ejecutado correctamente o no.
	 */
	public boolean update(AlbaranEntrada ae){ 
		boolean r=false;
		
		String sql="update albaranes_entrada set "
					+ "n_socio=?, "
					+ "fecha=?, "
				+ "where n_albaran=? and n_factura is null";
		
		JdbcTemplate jdbc=new JdbcTemplate(dataSource);
		
		try{
			int n=jdbc.update(
					sql,
					new Object[]{
							ae.getnSocio(),
							new java.sql.Date(ae.getFecha().getTime()),
							ae.getnAlbaran()});
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
		si queremos quitar un albaran de una factura pasar nFactura is null y el albarán 
		volvería a quedar sin estar facturado
	 * @param nAlbaran
	 * @param nFactura
	 * @return boolean -- Determina si la función se ha ejecutado correctamente o no.
	 */
	public boolean facturar(int nAlbaran, int nFactura){ 
				
		boolean r=false;
		
		System.out.println(nFactura +" "+ nAlbaran);
		if(nFactura>0){
			String sql="update albaranes_entrada set n_factura=? where n_albaran=?";
		
			JdbcTemplate jdbc=new JdbcTemplate(dataSource);
		
			try{
				int n=jdbc.update(
					sql,
					new Object[]{nFactura, nAlbaran});
				r=n>0;
			}
			catch(DataAccessException dae){
				dae.printStackTrace();
				System.out.println("facturar AlbaranEntrada - Error acceso de datos");
			}
		}
		
		else{
			
			String sql="update albaranes_entrada set n_factura=null where n_albaran=?";
			
			JdbcTemplate jdbc=new JdbcTemplate(dataSource);
		
			try{
				int n=jdbc.update(
					sql,
					new Object[]{nAlbaran});
				r=n>0;
			}
			catch(DataAccessException dae){
				dae.printStackTrace();
				System.out.println("facturar AlbaranEntrada - Error acceso de datos");
			}
		}
			return r;
	}
	
	/**
	 * Creamos una función que devuelve una lista con todos los albaranes de entrada.
	 * @return lista
	 */
	public List<AlbaranEntrada> listar(){
		List<AlbaranEntrada> lista;
		
		JdbcTemplate jdbc=new JdbcTemplate(dataSource);
		String sql="select * from albaranes_entrada where n_factura is null order by fecha desc";
		lista=jdbc.query(sql,new AlbaranEntradaRowMapper());
		return lista;
	}
	
	/**
	 * Función sobrecargada para buscar por cifNif
	 * Sql by laura y marco
	 * @param cifNif
	 * @return lista
	 */
	public List<AlbaranEntrada> listar(String cifNif){ //Devuelve todos los albaranes por cifNif
		List<AlbaranEntrada> lista;
		
		JdbcTemplate jdbc=new JdbcTemplate(dataSource);
		String sql="SELECT albaranes_entrada.n_albaran,albaranes_entrada.n_socio,"
				+ "albaranes_entrada.fecha,albaranes_entrada.n_factura "
				+ "from albaranes_entrada "
				  + "join agricultores on (albaranes_entrada.n_socio=agricultores.n_socio) "
				  + "JOIN personas on (personas.id_persona=agricultores.id_persona)"
				+ "WHERE cif_nif=? order by albaranes_entrada.fecha desc ";
		lista=jdbc.query(sql,new Object[]{cifNif},new AlbaranEntradaRowMapper());
		return lista;
	}
	
	/**
	 * Función sobrecargada para buscar por fecha.
	 * @param fecha
	 * @return  lista -- Devuelve una lista con todos los objetos AlbaranEntrada
	 */
	public List<AlbaranEntrada> listar(Date fecha){ 
		List<AlbaranEntrada> lista;
		
		JdbcTemplate jdbc=new JdbcTemplate(dataSource);
		String sql="select * from albaranes_entrada where fecha=?";
		java.sql.Date d=new java.sql.Date(fecha.getTime());
		lista=jdbc.query(sql,new Object[]{d},new AlbaranEntradaRowMapper());
		return lista;
	}
	
	/**
	 * Función para acotar la fecha de busqueda
	 * @param fechaInicio
	 * @param fechaFinal
	 * @return lista
	 */
	public List<AlbaranEntrada> buscarFecha (Date fechaInicio, Date fechaFinal){
		
		List<AlbaranEntrada> lista;
		JdbcTemplate jdbc=new JdbcTemplate(dataSource);
		String sql="select * from albaranes_entrada where fecha BETWEEN ? AND ? order by fecha desc;";
		java.sql.Date fi=new java.sql.Date(fechaInicio.getTime());
		java.sql.Date ff=new java.sql.Date(fechaFinal.getTime());
		lista=jdbc.query(sql,new Object[]{fi,ff},new AlbaranEntradaRowMapper());
		return lista;
	}
	
	
	
	public List <AlbaranEntrada> listar (int nAlbaran){
		List<AlbaranEntrada> lista;
		JdbcTemplate jdbc=new JdbcTemplate(dataSource);
		String sql="select n_albaran,n_socio,"
				+ "fecha,n_factura "
				+ "from albaranes_entrada"
				+ " where n_albaran=?";
		lista = jdbc.query (sql, new Object []{nAlbaran}, new AlbaranEntradaRowMapper());
		return lista;
	}
	
	
	/**
	 * Función sobrecargada que devuelve una lista con todos los albaranes no facturados.
	 * Sql by laura y marco
	 * @param cifNif
	 * @return lista
	 */
	public List<AlbaranEntrada> listarPendientes(String cifNif){ 
		
		List<AlbaranEntrada> lista;
		
		JdbcTemplate jdbc=new JdbcTemplate(dataSource);
		String sql="SELECT albaranes_entrada.n_albaran,albaranes_entrada.n_socio,"
				+ "albaranes_entrada.fecha,albaranes_entrada.n_factura from albaranes_entrada"
				+ " join agricultores on(agricultores.n_socio=albaranes_entrada.n_socio) JOIN "
				+ "personas on (personas.id_persona=agricultores.id_persona)WHERE personas.cif_nif=?"
				+ " and albaranes_entrada.n_factura is null;";
		lista=jdbc.query(sql,new Object[]{cifNif},new AlbaranEntradaRowMapper());
		return lista;
	}
	
	/**
	 * Borra un albaran, hay que controlar que sea un albarán que nFactura sea nulo, es decir, que no esté facturado
	 * @param nAlbaran
	 * @return boolean -- Devuelve un boolean que comprueba si se ha ejecutado correctamente o no.
	 */
	public boolean delete(int nAlbaran){ 
		String sql="delete from albaranes_entrada where n_albaran=? and n_factura is NULL";
		
		JdbcTemplate jdbc=new JdbcTemplate(dataSource);
		
		int n=jdbc.update(sql,new Object[]{nAlbaran});

		return n>0;
	}

	/**
	 * Calcula el precio total, teníendo el iva y el precio neto.
	 * @param nAlbaran
	 * @return
	 */
	public double calcularPrecioE(int nAlbaran){
		double precio = 0;
		
		String sql="select if(sum(lineas_albaranes_e.precio_kg*lineas_albaranes_e.peso) "
				+ "is null,0,sum(lineas_albaranes_e.precio_kg*lineas_albaranes_e.peso)) "
				+ "from lineas_albaranes_e where lineas_albaranes_e.n_albaran=?";
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
	public double calcularPrecioUnaLineaE (int idLinea){
		double precio = 0;
		
		String sql="select lineas_albaranes_e.precio_kg*lineas_albaranes_e.peso "
				+ "from lineas_albaranes_e where lineas_albaranes_e.id_linea_e=?";
		JdbcTemplate jdbc=new JdbcTemplate(dataSource);
		
		precio=jdbc.queryForObject(sql,new Object[]{idLinea},Double.class);
		
		return precio;
	}
	
	/**
	 * Función que lee y devuelve un objeto AlbaranEntrada. Se busca por nAlbaran
	 * @param nAlbaran
	 * @return ae
	 */
	public AlbaranEntrada readConDetalles(int nAlbaran){
		AlbaranEntrada as=read(nAlbaran);
		
		as.setLineas(daoLineas.listar(nAlbaran));
		
		return as;
	}
	
	public List<AlbaranEntrada> listarConDetalle(int nFactura){
		ArrayList<AlbaranEntrada> l=new ArrayList<AlbaranEntrada>();
		
		JdbcTemplate jdbc=new JdbcTemplate(dataSource);
		
		String sql="select n_albaran from albaranes_entrada where n_factura=?";
		List<Integer> ln=jdbc.queryForList(sql,new Object[]{nFactura},Integer.class);
		for(Integer na:ln){
			AlbaranEntrada alb=readConDetalles(na);
			l.add(alb);
		}
		
		return l;
	}	
	
}
