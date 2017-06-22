package test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import dao.DAOAlbaranSalida;
import dao.DAOCliente;
import dao.DAOFacturaSalida;
import dao.DAOLineaAlbaranSalida;
import dao.DAOPersona;
import dao.DAOVariedades;
import junit.framework.TestCase;
import modelos.AlbaranSalida;
import modelos.Cliente;
import modelos.FacturaSalida;
import modelos.LineaAlbaranSalida;
import modelos.Persona;
import modelos.Variedades;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:Spring-Beans.xml"})
public class DAOFacturaSalidaTest extends TestCase{
	@Autowired
	DAOLineaAlbaranSalida daola;
	
	@Autowired
	DAOAlbaranSalida daoal;
	
	@Autowired
	DAOCliente daoc;
	
	@Autowired
	DAOPersona daop;
	
	@Autowired
	DAOVariedades daov;
	
	@Autowired
	DAOFacturaSalida daof;
	
	private static Date h;
	
	@BeforeClass
	public static void preparar(){
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		h = new Date();
		
		try{
			h = formatter.parse(formatter.format(h));
		}
		catch(ParseException tpe){
			fail();
		}
	}
	
	@Test
	public void testCreate(){
		
		//Creo una factura
		FacturaSalida fac=new FacturaSalida(-1,h,21,0,false);
		daof.create(fac);
		
		FacturaSalida x=daof.read(fac.getnFactura());
		
		assertEquals(fac.getnFactura(),x.getnFactura());
		assertEquals(fac.getFecha(),x.getFecha());
		assertEquals(fac.getIva(),x.getIva());
		assertEquals(fac.getPrecioNeto(),fac.getPrecioNeto());
		assertEquals(fac.isAnulacion(),x.isAnulacion());
		
		daof.delete(fac.getnFactura());
	}
	
	@Test
	public void testCalcularPrecio(){
		
		//Creo un Cliente
		Persona per=new Persona(-1, "B45263965", "Cerezas S.A.", null, "toledo", "689526341", "cerezas@gmail.com");
		daop.create(per);
					
		Cliente agri=new Cliente(per.getIdPersona(), per.getCifNif(), per.getNombreRazonSocial(), per.getApellidos(), per.getDireccion(), per.getTelefono(), per.getEmail(), -1, false);
		daoc.create(agri);
						
		//Creo un albaran
		AlbaranSalida a=new AlbaranSalida(-1,agri.getnCliente(),h,0);
		daoal.create(a);
						
		//Creo una varidad
		Variedades v=new Variedades("Pepinos", 2.8, 2.5, 4.88);
		daov.create(v);
						
		//Creo una linea de albaran
		LineaAlbaranSalida las = new LineaAlbaranSalida(a.getnAlbaran(),-1,v.getTipo(),5,v.getPesoCaja(),v.getPrecioCaja());
		daola.create(las);
		
		//Creo otra linea de albaran
		LineaAlbaranSalida las2 = new LineaAlbaranSalida(a.getnAlbaran(),-1,v.getTipo(),8,v.getPesoCaja(),v.getPrecioCaja());
		daola.create(las2);
		
		//Creo otro albaran
		AlbaranSalida a2=new AlbaranSalida(-1,agri.getnCliente(),h,0);
		daoal.create(a2);
		
		//Creo una linea en albaran a2
		LineaAlbaranSalida las3 = new LineaAlbaranSalida(a2.getnAlbaran(),-1,v.getTipo(),1,v.getPesoCaja(),v.getPrecioCaja());
		daola.create(las3);
									
		//Creo una factura
		FacturaSalida fac=new FacturaSalida(-1,h,21,0,false);
		daof.create(fac);
		
		//Meto esta factura en los dos albaranes creados
		a.setnFactura(fac.getnFactura());
		a2.setnFactura(fac.getnFactura());
		
		daoal.facturar(a.getnAlbaran(), fac.getnFactura());
		daoal.facturar(a2.getnAlbaran(), fac.getnFactura());
				
		//Calculo el importe neto del albaran 1
		double precio=daoal.calcularPrecio(a.getnAlbaran());
		System.out.println("El precio neto de este albaran es " + precio);
		
		//Calculo el importe neto del albaran 2
		double precio2=daoal.calcularPrecio(a2.getnAlbaran());
		System.out.println("El precio neto de este albaran es " + precio2);
		
		//Calculo el importe neto de la factura
		double total=daof.calcularPrecioFactura(fac.getnFactura());
		System.out.println("El precio neto de esta factura es " + total);
		
		//Meto este valor en la factura
		fac.setPrecioNeto(total);
		
		assertEquals(fac.getPrecioNeto(),(precio+precio2));
				
		daof.delete(fac.getnFactura());
		daoal.delete(a.getnAlbaran());
		daoal.delete(a2.getnAlbaran());
		daop.delete(per.getIdPersona());
		daov.delete(v.getTipo());
		
	}
	
	@Test
	public void testUpdate(){
		
		//Creo una factura
		FacturaSalida fac=new FacturaSalida(-1,h,21,0,false);
		daof.create(fac);
		
		//Creo otra factura con fecha e iva diferente pero mismo nFactura
		Date d=new Date(h.getTime()-86400000);
		
		FacturaSalida fac2=new FacturaSalida(fac.getnFactura(),d,19,0,false);
		daof.update(fac2);
				
		FacturaSalida x=daof.read(fac.getnFactura());
				
		assertEquals(fac2.getnFactura(),x.getnFactura());
		assertEquals(fac2.getFecha(),x.getFecha());
		assertEquals(fac2.getIva(),x.getIva());
		assertEquals(fac2.getPrecioNeto(),fac.getPrecioNeto());
		assertEquals(fac2.isAnulacion(),x.isAnulacion());
				
		daof.delete(fac.getnFactura());
	}
	
	@Test
	public void testListar(){
		
		//Creo un Cliente
		Persona per=new Persona(-1, "B45263965", "Cerezas S.A.", null, "toledo", "689526341", "cerezas@gmail.com");
		daop.create(per);
				
		Cliente agri=new Cliente(per.getIdPersona(), per.getCifNif(), per.getNombreRazonSocial(), per.getApellidos(), per.getDireccion(), per.getTelefono(), per.getEmail(), -1, false);
		daoc.create(agri);
				
		//Creo un Albaran
		AlbaranSalida a=new AlbaranSalida(-1,agri.getnCliente(),h,0);
		daoal.create(a);
		
		//Creo otro albaran en otra fecha
		Date d=new Date(h.getTime()-86400000);
		AlbaranSalida a2=new AlbaranSalida(-1,agri.getnCliente(),d,0);
		daoal.create(a2);
		
		//Creo una factura con a
		FacturaSalida fac=new FacturaSalida(-1,h,21,0,false);
		daof.create(fac);
		a.setnFactura(fac.getnFactura());
		daoal.facturar(a.getnAlbaran(), fac.getnFactura());
		
		//Creo otra factura con a2
		FacturaSalida fac2=new FacturaSalida(-1,h,21,0,false);
		daof.create(fac2);
		a2.setnFactura(fac2.getnFactura());
		daoal.facturar(a2.getnAlbaran(), fac2.getnFactura());
		
		List<FacturaSalida> lista = daof.listar(); //Listar todos
		assertTrue(lista.size()>0);
		System.out.println("La lista tiene " + lista.size() + " elementos."); //Debe imprimir 2
		
		lista = daof.listar("B45263965"); //Listar por DNI
		assertTrue(lista.size()>0);
		System.out.println("La lista tiene " + lista.size() + " elementos."); //Debe imprimir 2
		
		lista = daof.buscarFecha(d,h); //Listar por fechas
		assertTrue(lista.size()>0);
		System.out.println("La lista tiene " + lista.size() + " elementos."); //Debe imprimir 2
		
		daof.delete(fac.getnFactura());
		daof.delete(fac2.getnFactura());
		daoal.delete(a.getnAlbaran());
		daoal.delete(a2.getnAlbaran());
		daoc.delete(agri.getnCliente());
		daop.delete(per.getIdPersona());
	}
	
	@Test
	public void testAnularFactura(){
		
		//Creo una factura
		FacturaSalida fac=new FacturaSalida(-1,h,21,0,false);
		daof.create(fac);
		
		//Anulo la factura
		fac.setAnulacion(true);
		daof.anularFactura(fac.getnFactura());
		
		FacturaSalida x=daof.read(fac.getnFactura());
		
		assertEquals(fac.getnFactura(),x.getnFactura());
		assertEquals(fac.getFecha(),x.getFecha());
		assertEquals(fac.getIva(),x.getIva());
		assertEquals(fac.getPrecioNeto(),fac.getPrecioNeto());
		assertEquals(fac.isAnulacion(),x.isAnulacion());
		
		daof.delete(fac.getnFactura());
	}
	
}
