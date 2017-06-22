package test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import dao.DAOVariedades;
import junit.framework.TestCase;
import modelos.Variedades;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:Spring-Beans.xml"})
public class DAOVariedadesTest extends TestCase{
	
	@Autowired
	DAOVariedades dao;
	
	@Test
	public void testCreate(){
		
		Variedades v=new Variedades("pepino 1", 2.8, 2.5, 4.88);
		dao.create(v);
		
		Variedades u=dao.read(v.getTipo());
		
		assertEquals(v.getTipo(),u.getTipo());
		assertEquals(v.getPrecioKg(),u.getPrecioKg());
		assertEquals(v.getPesoCaja(),u.getPesoCaja());
		assertEquals(v.getPrecioCaja(),u.getPrecioCaja());
		
		dao.delete(v.getTipo());
	}
	
	@Test
	public void testUpdate(){
		
		Variedades s=new Variedades("pepino 1", 2.8, 2.5, 4.88);
		dao.create(s);
		
		Variedades u=new Variedades(s.getTipo(), 3, 5.2, 6.9);
		dao.update(u);
		
		Variedades v=dao.read(s.getTipo());
		
		assertEquals(v.getTipo(),u.getTipo());
		assertEquals(v.getPrecioKg(),u.getPrecioKg());
		assertEquals(v.getPesoCaja(),u.getPesoCaja());
		assertEquals(v.getPrecioCaja(),u.getPrecioCaja());
		
		dao.delete(s.getTipo());
	}
	
	@Test
	public void testListar(){
		
		Variedades v=new Variedades("pepino 1", 2.8, 2.5, 4.88);
		dao.create(v);
		
		Variedades u=new Variedades("pepino 2", 3, 5.2, 6.9);
		dao.create(u);
		
		List<Variedades> lista = dao.listar();
		assertTrue(lista.size()>0);
		//System.out.println("La lista tiene " + lista.size() + " elementos.");
		
		lista = dao.listar("1");
		assertTrue(lista.size()>0);
		//System.out.println("La lista tiene " + lista.size() + " elementos.");
		
		dao.delete(v.getTipo());
		dao.delete(u.getTipo());
	}
	
	
		
}
