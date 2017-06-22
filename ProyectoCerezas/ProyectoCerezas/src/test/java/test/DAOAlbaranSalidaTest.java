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

import dao.DAOCliente;
import dao.DAOAlbaranSalida;
import dao.DAOFacturaSalida;
import dao.DAOPersona;
import junit.framework.TestCase;
import modelos.Cliente;
import modelos.AlbaranSalida;
import modelos.FacturaSalida;
import modelos.Persona;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:Spring-Beans.xml"})
public class DAOAlbaranSalidaTest extends TestCase{

	@Autowired
	DAOAlbaranSalida dao;
	
	@Autowired
	DAOCliente daoa;
	
	@Autowired
	DAOPersona daop;
	
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
		
		Persona per=new Persona(-1, "B45263965", "Cerezas S.A.", null, "toledo", "689526341", "cerezas@gmail.com");
		daop.create(per);
		
		Cliente agri=new Cliente(per.getIdPersona(), per.getCifNif(), per.getNombreRazonSocial(), per.getApellidos(), per.getDireccion(), per.getTelefono(), per.getEmail(), -1, false);
		daoa.create(agri);
				
		AlbaranSalida a=new AlbaranSalida(-1,agri.getnCliente(),h,0);
		dao.create(a);
		
		AlbaranSalida u=dao.read(a.getnAlbaran());
		
		assertEquals(a.getnAlbaran(),u.getnAlbaran());
		assertEquals(a.getnCliente(),u.getnCliente());
		assertEquals(a.getFecha(),u.getFecha());
		assertEquals(a.getnFactura(),u.getnFactura());
		
		dao.delete(a.getnAlbaran());
		daoa.delete(agri.getnCliente());
		daop.delete(per.getIdPersona());
		
	}
	
	@Test
	public void testUpdate(){
		
		//Creo un Cliente
		Persona per=new Persona(-1, "B45263965", "Cerezas S.A.", null, "toledo", "689526341", "cerezas@gmail.com");
		daop.create(per);
		
		Cliente agri=new Cliente(per.getIdPersona(), per.getCifNif(), per.getNombreRazonSocial(), per.getApellidos(), per.getDireccion(), per.getTelefono(), per.getEmail(), -1, false);
		daoa.create(agri);
		
		//Creo otro Cliente
		Persona per2=new Persona(-1, "B45859268", "Peras S.A.", null, "Madrid", "689526341", "peras@gmail.com");
		daop.create(per2);
		
		Cliente agri2=new Cliente(per2.getIdPersona(), per2.getCifNif(), per2.getNombreRazonSocial(), per2.getApellidos(), per2.getDireccion(), per2.getTelefono(), per2.getEmail(), -1, false);
		daoa.create(agri2);
		
		//Creo un albaran asociado al Cliente1
		AlbaranSalida s=new AlbaranSalida(-1,agri.getnCliente(),h,0);
		dao.create(s);
		
		//Tomo una fecha diferente y al Cliente 2
		Date d=new Date(h.getTime()-86400000);
		
		AlbaranSalida u=new AlbaranSalida(s.getnAlbaran(),agri2.getnCliente(),d,s.getnFactura());
		dao.update(u);
		
		AlbaranSalida a=dao.read(s.getnAlbaran());
		
		assertEquals(a.getnAlbaran(),u.getnAlbaran());
		assertEquals(a.getnCliente(),u.getnCliente());
		assertEquals(a.getFecha(),u.getFecha());
		assertEquals(a.getnFactura(),u.getnFactura());
		
		dao.delete(s.getnAlbaran());
		daoa.delete(agri.getnCliente());
		daoa.delete(agri2.getnCliente());
		daop.delete(per.getIdPersona());
		daop.delete(per2.getIdPersona());
	}
	
	@Test
	public void testFacturar(){
		
		//Creo un Cliente
		Persona per=new Persona(-1, "B45263965", "Cerezas S.A.", null, "toledo", "689526341", "cerezas@gmail.com");
		daop.create(per);
		
		Cliente agri=new Cliente(per.getIdPersona(), per.getCifNif(), per.getNombreRazonSocial(), per.getApellidos(), per.getDireccion(), per.getTelefono(), per.getEmail(), -1, false);
		daoa.create(agri);
		
		//Creo un Albaran
		AlbaranSalida a=new AlbaranSalida(-1,agri.getnCliente(),h,0);
		dao.create(a);
		
		//Creamos una factura
		FacturaSalida fac=new FacturaSalida(-1,h,21,50,false);
		daof.create(fac);
		
		//System.out.println("El nFactura es: " + fac.getnFactura());
		
		//Modificamos el número factura en nuestro albaran
		AlbaranSalida s=new AlbaranSalida(a.getnAlbaran(),a.getnCliente(),a.getFecha(),fac.getnFactura());
		
		dao.facturar(s.getnAlbaran(), s.getnFactura());	//El albaran a ha quedado facturado
		a=dao.read(a.getnAlbaran());
		
		assertEquals(a.getnAlbaran(),s.getnAlbaran());
		assertEquals(a.getnCliente(),s.getnCliente());
		assertEquals(a.getFecha(),s.getFecha());
		assertEquals(a.getnFactura(),s.getnFactura());
	
		//Creo otro albaran en otra fecha
		Date d=new Date(h.getTime()-86400000);
		AlbaranSalida u=new AlbaranSalida(-1,agri.getnCliente(),d,0);
		dao.create(u);
		
		List<AlbaranSalida> lista = dao.listarPendientes("B45263965"); //Solicito sólo los albaranes no facturados
		//System.out.println("La lista tiene " + lista.size() + " elementos."); //Debería decir 1
		assertTrue(lista.size()>0);
		
		
		daof.delete(fac.getnFactura());
		dao.delete(a.getnAlbaran());
		dao.delete(u.getnAlbaran());
		daoa.delete(agri.getnCliente());
		daop.delete(per.getIdPersona());
	}
	
	@Test
	public void testListar(){
		
		//Creo un Cliente
		Persona per=new Persona(-1, "B45263965", "Cerezas S.A.", null, "toledo", "689526341", "cerezas@gmail.com");
		daop.create(per);
				
		Cliente agri=new Cliente(per.getIdPersona(), per.getCifNif(), per.getNombreRazonSocial(), per.getApellidos(), per.getDireccion(), per.getTelefono(), per.getEmail(), -1, false);
		daoa.create(agri);
				
		//Creo un Albaran
		AlbaranSalida a=new AlbaranSalida(-1,agri.getnCliente(),h,0);
		dao.create(a);
		
		//Creo otro albaran en otra fecha
		Date d=new Date(h.getTime()-86400000);
		AlbaranSalida u=new AlbaranSalida(-1,agri.getnCliente(),d,0);
		dao.create(u);
		
		List<AlbaranSalida> lista = dao.listar(); //Listar todos
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
		daoa.delete(agri.getnCliente());
		daop.delete(per.getIdPersona());
	}
	

}
