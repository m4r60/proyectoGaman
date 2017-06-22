package test;

import java.util.Properties;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import dao.DAOPersona;
import junit.framework.TestCase;
import modelos.Persona;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:Spring-Beans.xml"})
public class DAOPersonaTest extends TestCase {

	@Autowired
	DAOPersona dao;
	
	@Test
	public void testCreate(){
		
		Persona per=new Persona(-1, "B45263965", "Cerezas S.A.", null, "toledo", "689526341", "cerezas@gmail.com");
				
		Properties p=System.getProperties();
		System.out.println(p.getProperty("java.class.path"));
		dao.create(per);
		
		Persona u=dao.read(per.getCifNif());
		
		assertEquals(per.getCifNif(),u.getCifNif());
		assertEquals(per.getNombreRazonSocial(),u.getNombreRazonSocial());
		assertEquals(per.getApellidos(),u.getApellidos());
		assertEquals(per.getDireccion(),u.getDireccion());
		assertEquals(per.getTelefono(), u.getTelefono());
		assertEquals(per.getEmail(), u.getEmail());
		
		dao.delete(per.getIdPersona());
		
	}

}
