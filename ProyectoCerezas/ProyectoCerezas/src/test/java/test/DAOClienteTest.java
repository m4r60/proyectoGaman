package test;

import java.util.List;
import java.util.Properties;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import dao.DAOCliente;
import dao.DAOPersona;
import junit.framework.TestCase;
import modelos.Cliente;
import modelos.Persona;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:Spring-Beans.xml"})
public class DAOClienteTest  extends TestCase {

	@Autowired
	DAOCliente dao;
	
	@Autowired
	DAOPersona daop;
	
	
	@Test
	public void testCreate(){
		
		Properties p=System.getProperties();
		System.out.println(p.getProperty("java.class.path"));
		
		Persona per=new Persona(-1, "B45263965", "Cerezas S.A.", null, "toledo", "689526341", "cerezas@gmail.com");
		daop.create(per);
		
		
		Cliente a=new Cliente(per.getIdPersona(), per.getCifNif(), per.getNombreRazonSocial(), per.getApellidos(), per.getDireccion(), per.getTelefono(), per.getEmail(), -1, false);
		dao.create(a);
				
		Cliente u=dao.read(a.getIdPersona());
		
		assertEquals(a.getCifNif(),u.getCifNif());
		assertEquals(a.getNombreRazonSocial(),u.getNombreRazonSocial());
		assertEquals(a.getApellidos(),u.getApellidos());
		assertEquals(a.getDireccion(),u.getDireccion());
		assertEquals(a.getTelefono(), u.getTelefono());
		assertEquals(a.getEmail(), u.getEmail());
		assertEquals(a.getnSocio(),u.getnSocio());
		assertEquals(a.isBaja(),u.isBaja());
		
		dao.delete(a.getnCliente());
		daop.delete(per.getIdPersona());
		  
	}
	
	@Test
	public void testUpdate(){
		
		Persona per=new Persona(-1, "B45678985", "Manzanas S.A.", null, "toledo", "689526341", "cerezas@gmail.com");
		daop.create(per);
		Cliente a=new Cliente(per.getIdPersona(), per.getCifNif(), per.getNombreRazonSocial(), per.getApellidos(), per.getDireccion(), per.getTelefono(), per.getEmail(), -1, false);
		dao.create(a);
		
		//System.out.println("El numero de socio ahora es: " + a.getnSocio());
		
		Cliente u=new Cliente(a.getIdPersona(), "B45263312", "Perico s.a", null, "Madrid", "689516341", "cerezas@gmail.com", a.getnCliente(), false);
		dao.update(u);
		
		Cliente v=dao.read(u.getIdPersona());
		
		assertEquals(v.getCifNif(),u.getCifNif());
		assertEquals(v.getNombreRazonSocial(),u.getNombreRazonSocial());
		assertEquals(v.getApellidos(),u.getApellidos());
		assertEquals(v.getDireccion(),u.getDireccion());
		assertEquals(v.getTelefono(), u.getTelefono());
		assertEquals(v.getEmail(), u.getEmail());
		assertEquals(v.getnSocio(),u.getnSocio());
		assertEquals(v.isBaja(),u.isBaja());
		
		dao.delete(a.getnCliente());
		daop.delete(per.getIdPersona());
	} 

	@Test
	public void testRead(){
		
		Persona per=new Persona(-1, "B45263914", "Limones S.A.", null, "toledo", "689526341", "cerezas@gmail.com");
		daop.create(per);
		Cliente a=new Cliente(per.getIdPersona(), per.getCifNif(), per.getNombreRazonSocial(), per.getApellidos(), per.getDireccion(), per.getTelefono(), per.getEmail(), -1, false);
		dao.create(a);
		
		Persona per2=new Persona(-1,  "B45264500", "Melones S.A.", null, "toledo", "689526341", "cerezas@gmail.com");
		daop.create(per2);
		Cliente b=new Cliente(per2.getIdPersona(), per2.getCifNif(), per2.getNombreRazonSocial(), per2.getApellidos(), per2.getDireccion(), per2.getTelefono(), per2.getEmail(), -1, false);
		dao.create(b);
		
		List<Cliente> lista = dao.listar("689526341");
		assertTrue(lista.size()>0);
		//System.out.println("La lista tiene " + lista.size() + " elementos.");
		
		dao.delete(a.getnCliente());
		daop.delete(per.getIdPersona());
		dao.delete(b.getnCliente());
		daop.delete(per2.getIdPersona());
	}
	
	@Test
	public void testListar(){
		
		Persona per=new Persona(-1,"B45263960","Sandias S.A.", null, "toledo", "689526341", "cerezas@gmail.com");
		daop.create(per);
		Cliente a=new Cliente(per.getIdPersona(), per.getCifNif(), per.getNombreRazonSocial(), per.getApellidos(), per.getDireccion(), per.getTelefono(), per.getEmail(), -1, false);
		dao.create(a);
		
		Persona per2=new Persona(-1,"B45264502","Naranjas S.A.", null, "toledo", "689526341", "cerezas@gmail.com");
		daop.create(per2);
		Cliente b=new Cliente(per2.getIdPersona(), per2.getCifNif(), per2.getNombreRazonSocial(), per2.getApellidos(), per2.getDireccion(), per2.getTelefono(), per2.getEmail(), -1, false);
		dao.create(b);
		
		b.setBaja(true);
		
		dao.baja(b);
		
		List<Cliente> lista = dao.listar();
		assertTrue(lista.size()>0);
		//System.out.println("La lista tiene " + lista.size() + " elementos.");
		
		dao.delete(a.getnCliente());
		daop.delete(per.getIdPersona());
		dao.delete(b.getnCliente());
		daop.delete(per2.getIdPersona());
	}
}
