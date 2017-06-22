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

import dao.DAOAlbaranEntrada;
import dao.DAOAgricultor;
import dao.DAOFacturaEntrada;
import dao.DAOLineaAlbaranEntrada;
import dao.DAOPersona;
import dao.DAOVariedades;
import junit.framework.TestCase;
import modelos.AlbaranEntrada;
import modelos.Agricultor;
import modelos.FacturaEntrada;
import modelos.LineaAlbaranEntrada;
import modelos.Persona;
import modelos.Variedades;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:Spring-Beans.xml"})
public class DAOFacturaEntradaTest extends TestCase{
	@Autowired
	DAOLineaAlbaranEntrada daola;
	
	@Autowired
	DAOAlbaranEntrada daoal;
	
	@Autowired
	DAOAgricultor daoc;
	
	@Autowired
	DAOPersona daop;
	
	@Autowired
	DAOVariedades daov;
	
	@Autowired
	DAOFacturaEntrada daof;
	
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
		FacturaEntrada fac=new FacturaEntrada(-1,h,21,0,false);
		daof.create(fac);
		
		FacturaEntrada x=daof.read(fac.getnFactura());
		
		assertEquals(fac.getnFactura(),x.getnFactura());
		assertEquals(fac.getFecha(),x.getFecha());
		assertEquals(fac.getIva(),x.getIva());
		assertEquals(fac.getPrecioNeto(),fac.getPrecioNeto());
		assertEquals(fac.isAnulacion(),x.isAnulacion());
		
		daof.delete(fac.getnFactura());
	}
	
	@Test
	public void testCalcularPrecio(){
		
		//Creo un Agricultor
		Persona per=new Persona(-1, "B45263965", "Cerezas S.A.", null, "toledo", "689526341", "cerezas@gmail.com");
		daop.create(per);
					
		Agricultor agri=new Agricultor(per.getIdPersona(), per.getCifNif(), per.getNombreRazonSocial(), per.getApellidos(), per.getDireccion(), per.getTelefono(), per.getEmail(), -1, false);
		daoc.create(agri);
						
		//Creo un albaran
		AlbaranEntrada a=new AlbaranEntrada(-1,agri.getnSocio(),h,0);
		daoal.create(a);
						
		//Creo una varidad
		Variedades v=new Variedades("Pepinos", 2, 2.5, 5);
		daov.create(v);
						
		//Creo una linea de albaran
		LineaAlbaranEntrada las = new LineaAlbaranEntrada(a.getnAlbaran(),-1,v.getTipo(),5,v.getPrecioKg());
		daola.create(las);
		
		//Creo otra linea de albaran
		LineaAlbaranEntrada las2 = new LineaAlbaranEntrada(a.getnAlbaran(),-1,v.getTipo(),8,v.getPrecioKg());
		daola.create(las2);
		
		//Creo otro albaran
		AlbaranEntrada a2=new AlbaranEntrada(-1,agri.getnSocio(),h,0);
		daoal.create(a2);
		
		//Creo una linea en albaran a2
		LineaAlbaranEntrada las3 = new LineaAlbaranEntrada(a2.getnAlbaran(),-1,v.getTipo(),1,v.getPrecioKg());
		daola.create(las3);
									
		//Creo una factura
		FacturaEntrada fac=new FacturaEntrada(-1,h,21,0,false);
		daof.create(fac);
		
		//Meto esta factura en los dos albaranes creados
		a.setnFactura(fac.getnFactura());
		a2.setnFactura(fac.getnFactura());
		
		daoal.facturar(a.getnAlbaran(), fac.getnFactura());
		daoal.facturar(a2.getnAlbaran(), fac.getnFactura());
				
		//Calculo el importe neto del albaran 1
		double precio=daoal.calcularPrecioE(a.getnAlbaran());
		System.out.println("El precio neto de este albaran es " + precio);
		
		//Calculo el importe neto del albaran 2
		double precio2=daoal.calcularPrecioE(a2.getnAlbaran());
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
		FacturaEntrada fac=new FacturaEntrada(-1,h,21,0,false);
		daof.create(fac);
		
		//Creo otra factura con fecha e iva diferente pero mismo nFactura
		Date d=new Date(h.getTime()-86400000);
		
		FacturaEntrada fac2=new FacturaEntrada(fac.getnFactura(),d,19,0,false);
		daof.update(fac2);
				
		FacturaEntrada x=daof.read(fac.getnFactura());
				
		assertEquals(fac2.getnFactura(),x.getnFactura());
		assertEquals(fac2.getFecha(),x.getFecha());
		assertEquals(fac2.getIva(),x.getIva());
		assertEquals(fac2.getPrecioNeto(),fac.getPrecioNeto());
		assertEquals(fac2.isAnulacion(),x.isAnulacion());
				
		daof.delete(fac.getnFactura());
	}
	
	@Test
	public void testListar(){
		
		//Creo un Agricultor
		Persona per=new Persona(-1, "B45263965", "Cerezas S.A.", null, "toledo", "689526341", "cerezas@gmail.com");
		daop.create(per);
				
		Agricultor agri=new Agricultor(per.getIdPersona(), per.getCifNif(), per.getNombreRazonSocial(), per.getApellidos(), per.getDireccion(), per.getTelefono(), per.getEmail(), -1, false);
		daoc.create(agri);
				
		//Creo un Albaran
		AlbaranEntrada a=new AlbaranEntrada(-1,agri.getnSocio(),h,0);
		daoal.create(a);
		
		//Creo otro albaran en otra fecha
		Date d=new Date(h.getTime()-86400000);
		AlbaranEntrada a2=new AlbaranEntrada(-1,agri.getnSocio(),d,0);
		daoal.create(a2);
		
		//Creo una factura con a
		FacturaEntrada fac=new FacturaEntrada(-1,h,21,0,false);
		daof.create(fac);
		a.setnFactura(fac.getnFactura());
		daoal.facturar(a.getnAlbaran(), fac.getnFactura());
		
		//Creo otra factura con a2
		FacturaEntrada fac2=new FacturaEntrada(-1,h,21,0,false);
		daof.create(fac2);
		a2.setnFactura(fac2.getnFactura());
		daoal.facturar(a2.getnAlbaran(), fac2.getnFactura());
		
		List<FacturaEntrada> lista = daof.listar(); //Listar todos
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
		daoc.delete(agri.getnSocio());
		daop.delete(per.getIdPersona());
	}
	
	@Test
	public void testAnularFactura(){
		
		//Creo una factura
		FacturaEntrada fac=new FacturaEntrada(-1,h,21,0,false);
		daof.create(fac);
		
		//Anulo la factura
		fac.setAnulacion(true);
		daof.anularFactura(fac.getnFactura());
		
		FacturaEntrada x=daof.read(fac.getnFactura());
		
		assertEquals(fac.getnFactura(),x.getnFactura());
		assertEquals(fac.getFecha(),x.getFecha());
		assertEquals(fac.getIva(),x.getIva());
		assertEquals(fac.getPrecioNeto(),fac.getPrecioNeto());
		assertEquals(fac.isAnulacion(),x.isAnulacion());
		
		daof.delete(fac.getnFactura());
	}
	
}