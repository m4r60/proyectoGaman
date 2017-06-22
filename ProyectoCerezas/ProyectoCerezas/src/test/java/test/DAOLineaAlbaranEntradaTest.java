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
import dao.DAOLineaAlbaranEntrada;
import dao.DAOPersona;
import dao.DAOVariedades;
import junit.framework.TestCase;
import modelos.AlbaranEntrada;
import modelos.Agricultor;
import modelos.LineaAlbaranEntrada;
import modelos.Persona;
import modelos.Variedades;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:Spring-Beans.xml"})
public class DAOLineaAlbaranEntradaTest extends TestCase{
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
		
		//Creo un Agricultor
		Persona per=new Persona(-1, "B45263965", "Cerezas S.A.", null, "toledo", "689526341", "cerezas@gmail.com");
		daop.create(per);
			
		Agricultor agri=new Agricultor(per.getIdPersona(), per.getCifNif(), per.getNombreRazonSocial(), per.getApellidos(), per.getDireccion(), per.getTelefono(), per.getEmail(), -1, false);
		daoc.create(agri);
		
		//Creo un albaran
		AlbaranEntrada a=new AlbaranEntrada(-1,agri.getnSocio(),h,0);
		daoal.create(a);
		
		//Creo una varidad
		Variedades v=new Variedades("pepino 1", 2.8, 2.5, 4.88);
		daov.create(v);
		
		//Creo una linea de albaran
		LineaAlbaranEntrada las = new LineaAlbaranEntrada(a.getnAlbaran(),-1,v.getTipo(),5,v.getPrecioKg());
		daola.create(las);
		
		LineaAlbaranEntrada x=daola.read(las.getIdLinea());
		
		assertEquals(las.getnAlbaran(),x.getnAlbaran());
		assertEquals(las.getIdLinea(),x.getIdLinea());
		assertEquals(las.getTipo(),x.getTipo());
		assertEquals(las.getPeso(),x.getPeso());
		assertEquals(las.getPrecioKg(),x.getPrecioKg());
		
		daola.delete(las.getIdLinea());
		daoal.delete(a.getnAlbaran());
		daoc.delete(agri.getnSocio());
		daop.delete(per.getIdPersona());
		daov.delete(v.getTipo());
		
	}
	
	@Test
	public void testUpdate(){
		
		//Creo un Agricultor
		Persona per=new Persona(-1, "B45263965", "Cerezas S.A.", null, "toledo", "689526341", "cerezas@gmail.com");
		daop.create(per);
				
		Agricultor agri=new Agricultor(per.getIdPersona(), per.getCifNif(), per.getNombreRazonSocial(), per.getApellidos(), per.getDireccion(), per.getTelefono(), per.getEmail(), -1, false);
		daoc.create(agri);
				
		//Creo un albaran
		AlbaranEntrada a=new AlbaranEntrada(-1,agri.getnSocio(),h,0);
		daoal.create(a);
				
		//Creo dos varidades
		Variedades v=new Variedades("pepino 1", 2.8, 2.5, 4.88);
		daov.create(v);
		Variedades v2=new Variedades("pepino 2", 4.8, 3, 6.55);
		daov.create(v2);
				
		//Creo una linea de albaran
		LineaAlbaranEntrada las = new LineaAlbaranEntrada(a.getnAlbaran(),-1,v.getTipo(),5,v.getPrecioKg());
		daola.create(las);
		
		//Creo otra linea de albaran con Tipo2 y otro peso pero con el mismo idLinea que la anterior y la uso para modificar la base de datos
		LineaAlbaranEntrada x = new LineaAlbaranEntrada(a.getnAlbaran(),las.getIdLinea(),v2.getTipo(),8,v2.getPrecioKg());
		daola.update(x);
		
		LineaAlbaranEntrada t=daola.read(las.getIdLinea());
		
		assertEquals(t.getnAlbaran(),x.getnAlbaran());
		assertEquals(t.getIdLinea(),x.getIdLinea());
		assertEquals(t.getTipo(),x.getTipo());
		assertEquals(t.getPeso(),x.getPeso());
		assertEquals(t.getPrecioKg(),x.getPrecioKg());
		
		daola.delete(las.getIdLinea());
		daoal.delete(a.getnAlbaran());
		daoc.delete(agri.getnSocio());
		daop.delete(per.getIdPersona());
		daov.delete(v.getTipo());
		daov.delete(v2.getTipo());
		
	}
	
	@Test
	public void testListar(){
		//Creo un Agricultor
		Persona per=new Persona(-1, "B45263965", "Cerezas S.A.", null, "toledo", "689526341", "cerezas@gmail.com");
		daop.create(per);
						
		Agricultor agri=new Agricultor(per.getIdPersona(), per.getCifNif(), per.getNombreRazonSocial(), per.getApellidos(), per.getDireccion(), per.getTelefono(), per.getEmail(), -1, false);
		daoc.create(agri);
						
		//Creo un albaran
		AlbaranEntrada a=new AlbaranEntrada(-1,agri.getnSocio(),h,0);
		daoal.create(a);
						
		//Creo dos varidades
		Variedades v=new Variedades("pepino 1", 2.8, 2.5, 4.88);
		daov.create(v);
		Variedades v2=new Variedades("pepino 2", 4.8, 3, 6.55);
		daov.create(v2);
						
		//Creo dos lineas de albaran
		LineaAlbaranEntrada las = new LineaAlbaranEntrada(a.getnAlbaran(),-1,v.getTipo(),5,v.getPrecioKg());
		daola.create(las);
				
		LineaAlbaranEntrada x = new LineaAlbaranEntrada(a.getnAlbaran(),-1,v2.getTipo(),8,v2.getPrecioKg());
		daola.create(x);
		
		List<LineaAlbaranEntrada> lista = daola.listar(a.getnAlbaran()); //Lista las lineas de un albaran
		System.out.println("La lista tiene " + lista.size() + " elementos."); //Debe imprimir 2
		assertTrue(lista.size()>0);
		
		//Creo otro albaran con una línea de albaran
		AlbaranEntrada b=new AlbaranEntrada(-1,agri.getnSocio(),h,0);
		daoal.create(b);
		LineaAlbaranEntrada y = new LineaAlbaranEntrada(b.getnAlbaran(),-1,v.getTipo(),5,v.getPrecioKg());
		daola.create(y);
		
		//Listo los albaranes de este nuevo albaran
		List<LineaAlbaranEntrada> lista2 = daola.listar(b.getnAlbaran());
		System.out.println("La lista tiene " + lista2.size() + " elementos."); //Debe imprimir 1
		assertTrue(lista2.size()>0);
		
		//daola.delete(las.getIdLinea());
		//daola.delete(x.getIdLinea());
		//daola.delete(y.getIdLinea());
		
		//Borro los albaranes y las lineas que tiene dentro
		daoal.delete(a.getnAlbaran());
		daoal.delete(b.getnAlbaran());
		
		//Borro al Agricultor
		daoc.delete(agri.getnSocio());
		daop.delete(per.getIdPersona());
		
		//Borro las variedades
		daov.delete(v.getTipo());
		daov.delete(v2.getTipo());
	}
}
