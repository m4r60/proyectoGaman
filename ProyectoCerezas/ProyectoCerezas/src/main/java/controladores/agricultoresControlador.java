package controladores;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import dao.DAOAgricultorImpl;
import dao.DAOPersonaImpl;
import modelos.Agricultor;
import modelos.Persona;


@Controller
public class agricultoresControlador {
	@Autowired
	DAOAgricultorImpl daoa;
	@Autowired
	DAOPersonaImpl daop;


	
	/*CREAR*/
	/**
	 * @param sesion
	 * @param cifNif
	 * @return
	 */
	@RequestMapping(value={"/nuevoAgricultor"})
	public ModelAndView nuevoAgricultor(HttpSession sesion, 
			@RequestParam (value="cif_nif") String cifNif ){
		ModelAndView mv;
		Persona p= daop.read(cifNif);
		
		if(p==null){
			  mv = new ModelAndView("nuevoAgricultor");
			  mv.addObject("cifNif", cifNif);
			  
		  }else{
			  
			  Agricultor a=daoa.read(p.getIdPersona());
			  	if(a==null){
			  		Agricultor b = new Agricultor (p.getIdPersona(),false);
			  		boolean crearCliente= daoa.create(b);
			  		if (!crearCliente){
			  			System.out.println("no se ha podido crear");
			  		} else {
			  			System.out.println("sido creado");
			  		}
			  		Persona c = daop.read(b.getIdPersona());
			  		mv = buscarAgricultor(sesion,c.getCifNif());
			  	}else{
			  		mv= buscarAgricultor(sesion,p.getCifNif());	
			  	} 
		  }
	    	return mv;
	    }  	
		
	 /**
	  * @param sesion
	  * @return
	  */
	/*listado inicial*/
	@RequestMapping(value="/listadoAgricultores")
	public ModelAndView listadoAgricultores(
   		HttpSession sesion){
   	
   	ModelAndView mv = new ModelAndView("listadoAgricultores");
   	List <Agricultor> lista = daoa.listar();
   	
   	mv.addObject("listado",lista);
   	return mv;
   }
	/**
	 * @param sesion
	 * @param busqueda
	 * @return
	 */
	/*FILTRO */
	@RequestMapping(value="/FiltroListadoAgricultores")
	public ModelAndView buscarAgricultor(
			HttpSession sesion,
			@RequestParam(value="buscar_agricultor") String busqueda){
	
		ModelAndView mv = new ModelAndView("listadoAgricultores");
		List <Agricultor> lista= daoa.listar(busqueda);
		mv.addObject("listado" ,lista);
		return mv;
	}
	/**
	 * 
	 * @param sesion
	 * @param nSocio
	 * @return
	 */
	@RequestMapping(value={"/darBajaA"})
    public ModelAndView bajaA(
    		HttpSession sesion,
    		@RequestParam(value = "nSocio") int nSocio){
	   
	   Agricultor a = daoa.readModificar(nSocio);
	  a.setBaja(true);
	   daoa.baja(a);
	   
	   return listadoAgricultores(sesion);
    }
	
   /*EDITAR*/
   /**
    * @param sesion
    * @param nCliente
    * @return
    */
   @RequestMapping(value={"/modificarAgricultor"})
    public ModelAndView modificarAgricultor(
    		HttpSession sesion,
    		@RequestParam(value = "nSocio") int nSocio){
	   
	   ModelAndView mv = new ModelAndView("modificarAgricultor");  
	   Agricultor a=daoa.readModificar(nSocio);
	   
	   mv.addObject("agricultor", a);
	   return mv;
    }

   
   
   /*******************-VISTA NUEVO************************************/
   /**
    * @param sesion
    * @param nombre
    * @param apellido
    * @param cifNif
    * @param direccion
    * @param telefono
    * @param email
    * @return
    */
   @RequestMapping (value={"/crearAgricultor"})
   public ModelAndView crearAgricultor(HttpSession sesion,
		   @RequestParam (value="nombre_razon_social") String nombre,
		   @RequestParam (value="apellido") String apellido,
		   @RequestParam (value="cif_nif") String cifNif,
		   @RequestParam (value="direccion") String direccion,
		   @RequestParam (value="telefono") String telefono,
		   @RequestParam (value="email") String email){
	   
	   ModelAndView mv;
	   Persona p= new Persona (-1, cifNif,nombre, apellido, direccion, telefono, email);
	   boolean creadoPersona=daop.create(p);
	   
	   if (!creadoPersona){
		   mv = new ModelAndView("nuevoAgricultor");
	   } else {
		   Agricultor a = new Agricultor (p.getIdPersona(),false);
		   boolean creadoAgricultor = daoa.create(a);
		   if (!creadoAgricultor){
			  mv = new ModelAndView("nuevoAgricultor");
			  } else {
				  mv = buscarAgricultor(sesion,p.getCifNif());
		   }
	   }
	return mv;
   }
   
   
   /*******************-VISTA MODIFICARCLIENTE************************************/
   /*EDITAR AGRICULTOR*/
   /**
    * 
    * @param sesion
    * @param idPersona
    * @param nSocio
    * @param nombre
    * @param apellido
    * @param cifNif
    * @param direccion
    * @param telefono
    * @param email
    * @param baja
    * @return
    */
   @RequestMapping(value={"/modificadoAgricultor"})
    public ModelAndView modificadoaGRICULTOR(
    		HttpSession sesion,
    		@RequestParam (value ="id_persona")int idPersona,
    		@RequestParam (value ="n_socio")int nSocio,
    		@RequestParam (value="nombre_razon_social") String nombre,
			@RequestParam (value="apellido") String apellido,
			@RequestParam (value="cif_nif") String cifNif,
			@RequestParam (value="direccion") String direccion,
			@RequestParam (value="telefono") String telefono,
			@RequestParam (value="email") String email,
			@RequestParam (value="baja") boolean baja){
	  	Agricultor a = new Agricultor (idPersona, cifNif, nombre, apellido, direccion, telefono, email, nSocio, baja);
	  	
	  	boolean modificar=false;
	  	modificar = daoa.update(a);
	 
	  	if (!modificar){
	  		return modificarAgricultor (sesion, nSocio);
	  	} else {
	  		return listadoAgricultores(sesion);
	  	}
	
    }
}

