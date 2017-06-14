package controladores;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import dao.DAOClienteImpl;
import dao.DAOPersonaImpl;
import modelos.Cliente;
import modelos.Persona;



@Controller
public class clientesControlador {
	
	/* 
	 * 
	 * CONTROLADOR PARA EL FLUJO DE TRABAJO DEL CLIENTE
	 * 
	 * */
	
	@Autowired
	DAOClienteImpl daoc;
	@Autowired
	DAOPersonaImpl daop;
	
	/************************************************************************************/
	/*VISTA LISTADOCLIENTES*/
		
		/**
		 * FUNCIONANDO
		 * @param sesion
		 * @param cifNif
		 * @return
		 */
	   /*Añadir nuevo Cliente*/
	   @RequestMapping(value={"/nuevoCliente"})
	    public ModelAndView nuevoCliente(
	    		HttpSession sesion,
	    		@RequestParam (value="cif_nif") String cifNif){
		  ModelAndView mv;
		  Persona p = daop.read(cifNif);
 
		 if(p==null){
			   /*FUNCIONA*/
			  //para crear persona y cliente
			  mv = new ModelAndView("nuevoCliente");
			  mv.addObject("cifNif", cifNif);
		  }else{
			  //persona existe 
			  System.out.println("Persona existe");
			  Cliente c=daoc.read(p.getIdPersona());
			  	if(c==null){
			  		//cliente no existe
			  		Cliente d = new Cliente (p.getIdPersona(),false);
			  		System.out.println("El cliente no existe");
			  		boolean crearCliente= daoc.create(d);
			  		if (!crearCliente){
			  			System.out.println("El cliente no se ha podido crear");
			  		} else {
			  			System.out.println("El cliente ha sido creado");
			  		}
			  		
			  		Persona s = daop.read(d.getIdPersona());
			  		mv = buscarCliente(sesion,s.getCifNif());
			  		
			  		
			  	}else{
			  		//cliente si existe
			  		System.out.println("El cliente si existe");
			  		mv= buscarCliente(sesion,p.getCifNif());
			  		
			  	} 
		  }
	    	return mv;
	    }  	
	
	 /**
	  * FUNCIONANDO
	  * @param sesion
	  * @return
	  */
	/*Listado clientes en alta, listado inicial*/
	@RequestMapping(value="/listadoClientes")
	public ModelAndView listadoClientes(
    		HttpSession sesion){
    	
    	ModelAndView mv = new ModelAndView("listadoClientes");
    	List <Cliente> lista = daoc.listar();
    	
    	mv.addObject("listado",lista);
    	return mv;
    }
	
	/**
	 * CONTROLADOR FUNCIONANDO
	 * @param sesion
	 * @param busqueda
	 * @return
	 */
	/*Busqueda para el filtro, es una busqueda generica, tanto altas como bajas*/
	@RequestMapping(value="/FiltroListadoClientes")
	public ModelAndView buscarCliente(
			HttpSession sesion,
			@RequestParam(value="buscar_cliente") String busqueda
			){
	
		ModelAndView mv = new ModelAndView("listadoClientes");
		List <Cliente> lista= daoc.listar(busqueda);
		mv.addObject("listado" ,lista);
		return mv;
	}
	
	
	/*Dar de baja a un cliente*/
/**
 * 
 * @param sesion
 * @param nCliente
 * @return
 */
	   @RequestMapping(value={"/darBaja"})
	    public ModelAndView baja(
	    		HttpSession sesion,
	    		@RequestParam(value = "nCliente") int nCliente){
		   
		   System.out.println(nCliente);
		 
		   Cliente c = daoc.readModificar(nCliente);
		   c.setBaja(true);
		   daoc.baja(c);
		   
		   return listadoClientes(sesion);
	    }
	   
	   /**
	    * FUNCIONANDO
	    * @param sesion
	    * @param nCliente
	    * @return
	    */
	   /*Editar cliente*/
	   @RequestMapping(value={"/modificarCliente"})
	    public ModelAndView modificarCliente(
	    		HttpSession sesion,
	    		@RequestParam(value = "nCliente") int nCliente){
		   ModelAndView mv = new ModelAndView("modificarCliente");  
		   
		   Cliente c = daoc.readModificar(nCliente);
		   
		   mv.addObject("cliente", c);
		   System.out.println("Controlador modificar cliente");
	    	return mv;
	    }
/************************************************************************************/
/*******************-VISTA NUEVOCLIENTE************************************/
	   /**
	    * FUNCIONANDO
	    * @param sesion
	    * @param nombre
	    * @param apellido
	    * @param cifNif
	    * @param direccion
	    * @param telefono
	    * @param email
	    * @return
	    */
	   @RequestMapping (value={"/crearCliente"})
	   public ModelAndView crearCliente(HttpSession sesion,
			   @RequestParam (value="nombre_razon_social") String nombre,
			   @RequestParam (value="apellido") String apellido,
			   @RequestParam (value="cif_nif") String cifNif,
			   @RequestParam (value="direccion") String direccion,
			   @RequestParam (value="telefono") String telefono,
			   @RequestParam (value="email") String email){
		   
		   ModelAndView mv;
		   Persona p= new Persona (-1, cifNif, nombre, apellido, direccion, telefono, email);
		   boolean creadoPersona=daop.create(p);
		   
		   if (!creadoPersona){
			   System.out.println("No se ha creado el objeto persona correctamente");
			   mv = new ModelAndView("nuevoCliente");
		   } else {
			   System.out.println("Se ha creado el objeto persona correctamente");
			   Cliente c = new Cliente (p.getIdPersona(),false);
			   boolean creadoCliente = daoc.create(c);
			   if (!creadoCliente){
				  mv = new ModelAndView("nuevoCliente");
				  System.out.println("No se ha creado el objeto cliente correctamente");
			   } else {
				   System.out.println("Se ha podido crear el cliente correctamente");
				   mv = buscarCliente(sesion,p.getCifNif());
			   }
		   }
		return mv;
	   }
	   /************************************************************************************/
	   /*******************-VISTA MODIFICARCLIENTE************************************/
	   /*Editar cliente*/
	   @RequestMapping(value={"/modificadoCliente"})
	    public ModelAndView modificadoCliente(
	    		HttpSession sesion,
	    		@RequestParam (value ="id_persona")int idPersona,
	    		@RequestParam (value ="n_cliente")int nCliente,
	    		@RequestParam (value="nombre_razon_social") String nombre,
				@RequestParam (value="apellido") String apellido,
				@RequestParam (value="cif_nif") String cifNif,
				@RequestParam (value="direccion") String direccion,
				@RequestParam (value="telefono") String telefono,
				@RequestParam (value="email") String email,
				@RequestParam (value="baja") boolean baja){
		   System.out.println(idPersona);
		  	Cliente c = new Cliente (idPersona, cifNif, nombre, apellido, direccion, telefono, email, nCliente, baja);
		  	System.out.println(c.getIdPersona());
		  	boolean modificar=false;
		  	
		  	modificar = daoc.update(c);
		 
		  	if (!modificar){
		  		System.out.println("No se ha podido modificar el cliente correctamente");
		  		 return modificarCliente (sesion, nCliente);
		  	} else {
		  		System.out.println("Se ha podido modificar el cliente");
		  		 return listadoClientes(sesion);
		  	}
		
	    }
}
