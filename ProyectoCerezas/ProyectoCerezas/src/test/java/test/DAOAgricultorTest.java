package test;

import java.util.List;
import java.util.Properties;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import dao.DAOAgricultor;
import dao.DAOPersona;
import junit.framework.TestCase;
import modelos.Agricultor;
import modelos.Persona;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:Spring-Beans.xml"})
public class DAOAgricultorTest extends TestCase {

	@Autowired
	DAOAgricultor dao;
	
	@Autowired
	DAOPersona daop;
	
	
	@Test
	public void testCreate(){
		
		Properties p=System.getProperties();
		System.out.println(p.getProperty("java.class.path"));
		
		Persona per=new Persona(-1, "B45263965", "Cerezas S.A.", null, "toledo", "689526341", "cerezas@gmail.com");
		daop.create(per);
		
		Agricultor a=new Agricultor(per.getIdPersona(), per.getCifNif(), per.getNombreRazonSocial(), per.getApellidos(), per.getDireccion(), per.getTelefono(), per.getEmail(), -1, false);
		dao.create(a);
				
		Agricultor u=dao.read(a.getIdPersona());
		
		assertEquals(a.getCifNif(),u.getCifNif());
		assertEquals(a.getNombreRazonSocial(),u.getNombreRazonSocial());
		assertEquals(a.getApellidos(),u.getApellidos());
		assertEquals(a.getDireccion(),u.getDireccion());
		assertEquals(a.getTelefono(), u.getTelefono());
		assertEquals(a.getEmail(), u.getEmail());
		assertEquals(a.getnSocio(),u.getnSocio());
		assertEquals(a.isBaja(),u.isBaja());
		
		dao.delete(a.getnSocio());
		daop.delete(per.getIdPersona());
		 
	}
	
	@Test
	public void testUpdate(){
		
		Persona per=new Persona(-1, "B45263965", "Cerezas S.A.", null, "toledo", "689526341", "cerezas@gmail.com");
		daop.create(per);
		
		Agricultor a=new Agricultor(per.getIdPersona(), per.getCifNif(), per.getNombreRazonSocial(), per.getApellidos(), per.getDireccion(), per.getTelefono(), per.getEmail(), -1, false);
		dao.create(a);
		
		//System.out.println("El numero de socio ahora es: " + a.getnSocio());
		
		Agricultor u=new Agricultor(a.getIdPersona(), "B45263312", "Perico s.a", null, "Madrid", "689516341", "cerezas@gmail.com", a.getnSocio(), false);
		dao.update(u);
		
		Agricultor v=dao.read(a.getIdPersona());
		
		assertEquals(v.getCifNif(),u.getCifNif());
		assertEquals(v.getNombreRazonSocial(),u.getNombreRazonSocial());
		assertEquals(v.getApellidos(),u.getApellidos());
		assertEquals(v.getDireccion(),u.getDireccion());
		assertEquals(v.getTelefono(), u.getTelefono());
		assertEquals(v.getEmail(), u.getEmail());
		assertEquals(v.getnSocio(),u.getnSocio());
		assertEquals(v.isBaja(),u.isBaja());
		
		dao.delete(a.getnSocio());
		daop.delete(per.getIdPersona());
	} 

	@Test
	public void testRead(){
		
		Persona per=new Persona(-1, "B45263965", "Cerezas S.A.", null, "toledo", "689526341", "cerezas@gmail.com");
		daop.create(per);
		Agricultor a=new Agricultor(per.getIdPersona(), per.getCifNif(), per.getNombreRazonSocial(), per.getApellidos(), per.getDireccion(), per.getTelefono(), per.getEmail(), -1, false);
		dao.create(a);
		
		Persona per2=new Persona(-1,  "B45264589", "Peras S.A.", null, "toledo", "689526341", "cerezas@gmail.com");
		daop.create(per2);
		Agricultor b=new Agricultor(per2.getIdPersona(), per2.getCifNif(), per2.getNombreRazonSocial(), per2.getApellidos(), per2.getDireccion(), per2.getTelefono(), per2.getEmail(), -1, false);
		dao.create(b);
		
		List<Agricultor> lista = dao.listar("689526341");
		assertTrue(lista.size()>0);
		//System.out.println("La lista tiene " + lista.size() + " elementos.");
		
		dao.delete(a.getnSocio());
		daop.delete(per.getIdPersona());
		dao.delete(b.getnSocio());
		daop.delete(per2.getIdPersona());
	}
	
	@Test
	public void testListar(){
		
		Persona per=new Persona(-1, "B45263965", "Cerezas S.A.", null, "toledo", "689526341", "cerezas@gmail.com");
		daop.create(per);
		Agricultor a=new Agricultor(per.getIdPersona(), per.getCifNif(), per.getNombreRazonSocial(), per.getApellidos(), per.getDireccion(), per.getTelefono(), per.getEmail(), -1, false);
		dao.create(a);
		
		Persona per2=new Persona(-1,  "B45264589", "Peras S.A.", null, "toledo", "689526341", "cerezas@gmail.com");
		daop.create(per2);
		Agricultor b=new Agricultor(per2.getIdPersona(), per2.getCifNif(), per2.getNombreRazonSocial(), per2.getApellidos(), per2.getDireccion(), per2.getTelefono(), per2.getEmail(), -1, false);
		dao.create(b);
		
		b.setBaja(true);
		
		dao.baja(b);
		
		List<Agricultor> lista = dao.listar();
		assertTrue(lista.size()>0);
		//System.out.println("La lista tiene " + lista.size() + " elementos.");
		
		dao.delete(a.getnSocio());
		daop.delete(per.getIdPersona());
		dao.delete(b.getnSocio());
		daop.delete(per2.getIdPersona());
	}
	
	
}
