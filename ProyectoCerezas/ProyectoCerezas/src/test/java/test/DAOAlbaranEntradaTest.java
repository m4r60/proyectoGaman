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

import dao.DAOAgricultor;
import dao.DAOAlbaranEntrada;
import dao.DAOFacturaEntrada;
import dao.DAOPersona;
import junit.framework.TestCase;
import modelos.Agricultor;
import modelos.AlbaranEntrada;
import modelos.FacturaEntrada;
import modelos.Persona;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:Spring-Beans.xml"})
public class DAOAlbaranEntradaTest extends TestCase{
	
	@Autowired
	DAOAlbaranEntrada dao;
	
	@Autowired
	DAOAgricultor daoa;
	
	@Autowired
	DAOPersona daop;
	
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
		
		Persona per=new Persona(-1, "B45263965", "Cerezas S.A.", null, "toledo", "689526341", "cerezas@gmail.com");
		daop.create(per);
		
		Agricultor agri=new Agricultor(per.getIdPersona(), per.getCifNif(), per.getNombreRazonSocial(), per.getApellidos(), per.getDireccion(), per.getTelefono(), per.getEmail(), -1, false);
		daoa.create(agri);
				
		AlbaranEntrada a=new AlbaranEntrada(-1,agri.getnSocio(),h,0);
		dao.create(a);
		
		AlbaranEntrada u=dao.read(a.getnAlbaran());
		
		assertEquals(a.getnAlbaran(),u.getnAlbaran());
		assertEquals(a.getnSocio(),u.getnSocio());
		assertEquals(a.getFecha(),u.getFecha());
		assertEquals(a.getnFactura(),u.getnFactura());
		
		dao.delete(a.getnAlbaran());
		daoa.delete(agri.getnSocio());
		daop.delete(per.getIdPersona());
		
	}
	
	@Test
	public void testUpdate(){
		
		//Creo un agricultor
		Persona per=new Persona(-1, "B45263965", "Cerezas S.A.", null, "toledo", "689526341", "cerezas@gmail.com");
		daop.create(per);
		
		Agricultor agri=new Agricultor(per.getIdPersona(), per.getCifNif(), per.getNombreRazonSocial(), per.getApellidos(), per.getDireccion(), per.getTelefono(), per.getEmail(), -1, false);
		daoa.create(agri);
		
		//Creo otro agricultor
		Persona per2=new Persona(-1, "B45859268", "Peras S.A.", null, "Madrid", "689526341", "peras@gmail.com");
		daop.create(per2);
		
		Agricultor agri2=new Agricultor(per2.getIdPersona(), per2.getCifNif(), per2.getNombreRazonSocial(), per2.getApellidos(), per2.getDireccion(), per2.getTelefono(), per2.getEmail(), -1, false);
		daoa.create(agri2);
		
		//Creo un albaran asociado al agricultor1
		AlbaranEntrada s=new AlbaranEntrada(-1,agri.getnSocio(),h,0);
		dao.create(s);
		
		//Tomo una fecha diferente y al agricultor 2
		Date d=new Date(h.getTime()-86400000);
		
		AlbaranEntrada u=new AlbaranEntrada(s.getnAlbaran(),agri2.getnSocio(),d,s.getnFactura());
		dao.update(u);
		
		AlbaranEntrada a=dao.read(s.getnAlbaran());
		
		assertEquals(a.getnAlbaran(),u.getnAlbaran());
		assertEquals(a.getnSocio(),u.getnSocio());
		assertEquals(a.getFecha(),u.getFecha());
		assertEquals(a.getnFactura(),u.getnFactura());
		
		dao.delete(s.getnAlbaran());
		daoa.delete(agri.getnSocio());
		daoa.delete(agri2.getnSocio());
		daop.delete(per.getIdPersona());
		daop.delete(per2.getIdPersona());
	}
	
	@Test
	public void testFacturar(){
		
		//Creo un Agricultor
		Persona per=new Persona(-1, "B45263965", "Cerezas S.A.", null, "toledo", "689526341", "cerezas@gmail.com");
		daop.create(per);
		
		Agricultor agri=new Agricultor(per.getIdPersona(), per.getCifNif(), per.getNombreRazonSocial(), per.getApellidos(), per.getDireccion(), per.getTelefono(), per.getEmail(), -1, false);
		daoa.create(agri);
		
		//Creo un Albaran
		AlbaranEntrada a=new AlbaranEntrada(-1,agri.getnSocio(),h,0);
		dao.create(a);
		
		//Creamos una factura
		FacturaEntrada fac=new FacturaEntrada(-1,h,21,50,false);
		daof.create(fac);
		
		//System.out.println("El nFactura es: " + fac.getnFactura());
		
		//Modificamos el número factura en nuestro albaran
		AlbaranEntrada s=new AlbaranEntrada(a.getnAlbaran(),a.getnSocio(),a.getFecha(),fac.getnFactura());
		
		dao.facturar(s.getnAlbaran(), s.getnFactura());	//El albaran a ha quedado facturado
		a=dao.read(a.getnAlbaran());
		
		assertEquals(a.getnAlbaran(),s.getnAlbaran());
		assertEquals(a.getnSocio(),s.getnSocio());
		assertEquals(a.getFecha(),s.getFecha());
		assertEquals(a.getnFactura(),s.getnFactura());
	
		//Creo otro albaran en otra fecha
		Date d=new Date(h.getTime()-86400000);
		AlbaranEntrada u=new AlbaranEntrada(-1,agri.getnSocio(),d,0);
		dao.create(u);
		
		List<AlbaranEntrada> lista = dao.listarPendientes("B45263965"); //Solicito sólo los albaranes no facturados
		assertTrue(lista.size()>0);
		//System.out.println("La lista tiene " + lista.size() + " elementos."); //Debería decir 1
		
		daof.delete(fac.getnFactura());
		dao.delete(a.getnAlbaran());
		dao.delete(u.getnAlbaran());
		daoa.delete(agri.getnSocio());
		daop.delete(per.getIdPersona());
	}
	
	@Test
	public void testListar(){
		
		//Creo un Agricultor
		Persona per=new Persona(-1, "B45263965", "Cerezas S.A.", null, "toledo", "689526341", "cerezas@gmail.com");
		daop.create(per);
				
		Agricultor agri=new Agricultor(per.getIdPersona(), per.getCifNif(), per.getNombreRazonSocial(), per.getApellidos(), per.getDireccion(), per.getTelefono(), per.getEmail(), -1, false);
		daoa.create(agri);
				
		//Creo un Albaran
		AlbaranEntrada a=new AlbaranEntrada(-1,agri.getnSocio(),h,0);
		dao.create(a);
		
		//Creo otro albaran en otra fecha
		Date d=new Date(h.getTime()-86400000);
		AlbaranEntrada u=new AlbaranEntrada(-1,agri.getnSocio(),d,0);
		dao.create(u);
		
		List<AlbaranEntrada> lista = dao.listar(); //Listar todos
		assertTrue(lista.size()>0);
		//System.out.println("La lista tiene " + lista.size() + " elementos."); //Debe imprimir 2
		
		lista = dao.listar("B45263965"); //Listar por DNI
		assertTrue(lista.size()>0);
		//System.out.println("La lista tiene " + lista.size() + " elementos."); //Debe imprimir 2
		
		lista = dao.buscarFecha(d,h); //Listar por fechas
		assertTrue(lista.size()>0);
		//System.out.println("La lista tiene " + lista.size() + " elementos."); //Debe imprimir 2
		
		dao.delete(a.getnAlbaran());
		dao.delete(u.getnAlbaran());
		daoa.delete(agri.getnSocio());
		daop.delete(per.getIdPersona());
	}
	

}
