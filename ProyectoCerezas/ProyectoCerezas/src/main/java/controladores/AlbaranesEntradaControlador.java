package controladores;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import Utils.DateUtils;
import dao.DAOAlbaranEntradaImpl;
import dao.DAOAgricultorImpl;
import dao.DAOLineaAlbaranEntradaImpl;
import dao.DAOPersonaImpl;
import dao.DAOVariedadesImpl;
import modelos.Agricultor;
import modelos.AlbaranEntrada;
import modelos.LineaAlbaranEntrada;
import modelos.Persona;
import modelos.Variedades;


@Controller
public class AlbaranesEntradaControlador {

	@Autowired
	DAOAlbaranEntradaImpl daoae;
	
	@Autowired
	DAOPersonaImpl daop;
	
	@Autowired
	DAOAgricultorImpl daoa;
	
	@Autowired
	DAOLineaAlbaranEntradaImpl daole;
	
	@Autowired
	DAOVariedadesImpl daov;
	
/*********************************************************************************************************/
/****************VISTA LISTADO SALIDA SALIDA*****************/
	
	/*FUNCIONANDO*/
	/*Función para mostrar el listado de albaran.
	 * Por defecto, la lista que muestra son los albarances con n_factura = null.*/
	@RequestMapping("/listarAlbaranEntrada")
	  public ModelAndView listarAlbaranEntrada(HttpSession sesion){
		ModelAndView mv = new ModelAndView("listarAlbaranEntrada");
		List <AlbaranEntrada> listadoE = daoae.listar();
		
		Persona p = null; 
		double precio = 0.0;
		for (int i = 0; i<listadoE.size();i++){ /*Con este bucle recorro el list
		y para cada una de las posiciones recupero su cifnif y lo asigno a cada línea.
		Para ello, en modelos de albaran salida he creado una propiedad cifnif y otra precio Neto. Asimismo, calculo el precio
		de cada albaran y lo asigno a cada albaran de salida en su propiedad precio Neto */

			p = daop.readAlbaranEntrada(listadoE.get(i).getnSocio()); 
			listadoE.get(i).setCifNif(p.getCifNif());  //Asigno el cifNif a cada albaran salida
			
			precio = daoae.calcularPrecioE(listadoE.get(i).getnAlbaran());
			listadoE.get(i).setPrecioNetoE(precio); //Asigno el precio neto
			
			Date fecha = listadoE.get(i).getFecha();
			String fechaStr = DateUtils.formatearFecha(fecha);
			listadoE.get(i).setFechaStr(fechaStr); //Formateo la fecha y la introduzco en el objeto para que sea mostrada por la vista
			
			
		}
		
		mv.addObject("listado", listadoE);
 
    	return mv;
    }
	
	/*Filtros*/
	
	/*FUNCIONADO*/
	/*Filtro por cifNif*/
	@RequestMapping("/FiltroAlbaranEntradaCifNif")
	  public ModelAndView filtroAlbaranEntradaCifNif(HttpSession sesion,
			  @RequestParam (value="cif_nif") String cifNif){
		
		ModelAndView mv = new ModelAndView("listarAlbaranEntrada");
		List <AlbaranEntrada> listadoE = daoae.listar(cifNif);
		
		Persona p = null; 
		double precio = 0.0;
		for (int i = 0; i<listadoE.size();i++){ /*Con este bucle recorro el list
		y para cada una de las posiciones recupero su cifnif y lo asigno a cada línea.
		Para ello en modelos de albaran salida he creado una propiedad cifnif. Asimismo calculo el precio
		de cada albaran y lo asigno a cada línea*/

			p = daop.readAlbaranEntrada(listadoE.get(i).getnSocio()); 
			listadoE.get(i).setCifNif(p.getCifNif()); 
			
			precio = daoae.calcularPrecioE(listadoE.get(i).getnAlbaran());
			listadoE.get(i).setPrecioNetoE(precio); //Asigno el precio neto
			
			Date fecha = listadoE.get(i).getFecha();
			String fechaStr = DateUtils.formatearFecha(fecha);
			listadoE.get(i).setFechaStr(fechaStr); //Formateo la fecha y la introduzco en el objeto para que sea mostrada por la vista
		}
		
		mv.addObject("listado", listadoE);
	
  	return mv;
  }
	
	/*FUNCIONANDO*/
	/*Filtro por Fecha -- Hay que revisar el datePicker y el dateUtils*/

	@RequestMapping("/FiltroAlbaranEntradaFecha")
	  public ModelAndView filtroAlbaranEntradaFecha(HttpSession sesion,
			  @RequestParam (value="fecha_inicio") String fechaInicio,
			  @RequestParam (value = "fecha_final") String fechaFinal){
		
		Date fi = DateUtils.parseFormato(fechaInicio);
		Date ff = DateUtils.parseFormato(fechaFinal);
		
		/*Creo dos variables una para asignar la fecha mayor y otra asignar la fecha menor*/
		/*Luego las asigno a fechaAnterior y posterior para que no de error. El error sería introducir 
		 * una fecha mayor en el primer campo y una fecha inferior en el segundo. Así daría igual en que
		 *  campo fuera la fecha mayor y la menor*/
		Date fechaAnterior = fi;
		Date fechaPosterior = ff;
		int comparar = ff.compareTo(fi); //Compara las dos fechas y devuelve un int que si es mayor de 0 quiere decir que ff<fi
		if (comparar<0){
			fechaAnterior = ff;
			fechaPosterior = fi;
		}
		ModelAndView mv = new ModelAndView("listarAlbaranEntrada");
		List <AlbaranEntrada> listadoE = daoae.buscarFecha(fechaAnterior, fechaPosterior); 
		
		Persona p = null; 
		double precio = 0.0;
		for (int i = 0; i<listadoE.size();i++){ /*Con este bucle recorro el list
		y para cada una de las posiciones recupero su cifnif y lo asigno a cada línea.
		Para ello en modelos de albaran salida he creado una propiedad cifnif. Asimismo calculo el precio
		de cada albaran y lo asigno a cada línea*/

			p = daop.readAlbaranEntrada(listadoE.get(i).getnSocio());
			listadoE.get(i).setCifNif(p.getCifNif()); 
			
			precio = daoae.calcularPrecioE(listadoE.get(i).getnAlbaran());
			listadoE.get(i).setPrecioNetoE(precio); //Asigno el precio neto
			
			Date fecha = listadoE.get(i).getFecha();
			String fechaStr = DateUtils.formatearFecha(fecha);
			listadoE.get(i).setFechaStr(fechaStr); //Formateo la fecha y la introduzco en el objeto para que sea mostrada por la vista
		}
		
		
		mv.addObject("listado", listadoE);
		
  	return mv;
  }
	
	/*FUNCIONANDO*/
	
	/*Filtro por nAlbaran*/
	@RequestMapping("/FiltroAlbaranEntradaAlbaran")
	public ModelAndView filtroAlbaranEntradaAlbaran(HttpSession sesion,
			  @RequestParam (value="n_albaran") int nAlbaran){
		
		ModelAndView mv = new ModelAndView("listarAlbaranEntrada");
		List <AlbaranEntrada> listadoE = new ArrayList <AlbaranEntrada>();
		AlbaranEntrada albaranEntrada = daoae.read(nAlbaran);
		
		Persona p = daop.readAlbaranEntrada(albaranEntrada.getnSocio());
		albaranEntrada.setCifNif(p.getCifNif()); //Asigno el cifNif para la tabla
		
		double precio = daoae.calcularPrecioE(albaranEntrada.getnAlbaran());
		albaranEntrada.setPrecioNetoE(precio); //Asigno el precio neto
		
		Date fecha = albaranEntrada.getFecha();
		String fechaStr = DateUtils.formatearFecha(fecha);
		albaranEntrada.setFechaStr(fechaStr); //Formateo la fecha y la introduzco en el objeto para que sea mostrada por la vista
		
		
		listadoE.add(albaranEntrada);
		mv.addObject("listado", listadoE);
	
	return mv;
	}
	
	/*FUNCIONANDO*/
	
	/*Modificar AlbaranEntrada*/ 
	@RequestMapping("/ModificarAlbaranEntrada")
	public ModelAndView modificarAlbaranEntrada(HttpSession sesion,
			  @RequestParam (value="n_albaran") int nAlbaran){ //Al darle a modificar recuperamos nAlbaran
	
		ModelAndView mv = new ModelAndView("editarAlbaranEntrada");
		AlbaranEntrada albaranEntrada = daoae.read(nAlbaran); //Recuperamos el objeto albaran
		
		/*Recuperamos la fecha para formatearla y para devolverla como string formateada.*/
		Date fecha = albaranEntrada.getFecha();
		String fechaFormateada = DateUtils.formatearFecha(fecha);
		albaranEntrada.setFechaStr(fechaFormateada);
		
		Persona p = daop.readAlbaranEntrada(albaranEntrada.getnSocio()); //Recuperamos el objeto persona asociado a ese albaran
		
		
		List <LineaAlbaranEntrada> listadoLineasE = daole.listar(nAlbaran); //Recuperamos un List con el listado de líneas asociado a ese albarán
		
		/*Asignación el precioTotal de linea a cada línea*/
		Double precioLinea = 0.0;
		for (int i = 0; i < listadoLineasE.size(); i++){ //Para poder asignar el precioTotal a cada una de las líneas he tenido que crear una propiedad en el constructor llamada PrecioTotal
			precioLinea = daoae.calcularPrecioUnaLineaE(listadoLineasE.get(i).getIdLinea()); //Calculo el precio total de una línea concreta
			listadoLineasE.get(i).setPrecioTotal(precioLinea); //A cada una de las líneas le asigno el precio total de esa línea
		}
		/*Calculo del precio total de todas las líneas*/
		Double precioTotal = daoae.calcularPrecioE(nAlbaran); //Calculamos el precio total de todas las líneas de albarán con nAlbaran de ?;
		
		/*También tengo que mandar los tipos de variedades que existen*/
		List <Variedades> listadoTipos = daov.listar();
		
		mv.addObject("listadoTipos", listadoTipos); // Envío las variedades disponibles para que aparezcan en añadir línea de albarán
		mv.addObject("precioTotal", precioTotal); //Devuelvo el precio total de ese albarán
		mv.addObject("listadoLineas", listadoLineasE); //Devolvemos un listado con todas las líneas de albarán.
		mv.addObject("albaranEntrada", albaranEntrada); //Pasamos un objeto albaran salida que rellenará por defecto el formulario de modificar
		mv.addObject("persona", p); //Pasamos un objeto persona para que muestre los datos del cliente

	return mv;
	}
	
	/*FUNCIONANDO*/
	
	/*Eliminar AlbaranSalida*/
	@RequestMapping("/EliminarAlbaranEntrada")
	public ModelAndView eliminarAlbaranEntrada(HttpSession sesion,
			  @RequestParam (value="n_albaran") int nAlbaran){ //Recupero el número de albarán
		daoae.delete(nAlbaran); //Con ese número de albarán lo borro, siempre y cuando no esté facturado.
		ModelAndView mv = listarAlbaranEntrada(sesion);

	return mv;
	}
	
	/*Nuevo AlbaranSalida*/
	@RequestMapping("/nuevoAlbaranEntrada")
	public ModelAndView nuevoAlbaranEntrada(HttpSession sesion,
			  @RequestParam (value="cif_nif") String cifNif){
		
		Date diaActual = new Date();
		//Primero metemos una ventana modal que nos pida el cifNif para recuperar persona.
		ModelAndView mv = listarAlbaranEntrada (sesion);
		Persona p = daop.read(cifNif);
		if (p==null){
			//Persona no existe
			System.out.println("No se puede crear albaran porque no existe persona");
			/*Nos manda a la vista listarAlbaran por defecto*/
		}else {
			//Persona existe
			Agricultor a = daoa.read(p.getIdPersona());
			if (a==null){
				//Cliente no existe
				System.out.println("No se puede crear albaran porque no existe socio");
			} else {
				//Cliente existe
				System.out.println("El cliente existe, por lo tanto, se puede crear albaran");
				
				List <Variedades> listadoVariedades = daov.listar();
				
				AlbaranEntrada albaranEntrada = new AlbaranEntrada(-1, a.getnSocio(),diaActual, 0);
				boolean creado = daoae.create(albaranEntrada);
				if (creado){
					mv = new ModelAndView ("editarAlbaranEntrada");
					
					Date fecha = albaranEntrada.getFecha();
					String fechaStr = DateUtils.formatearFecha(fecha);
					albaranEntrada.setFechaStr(fechaStr); //Formateo la fecha y la introduzco en el objeto para que sea mostrada por la vista
					
					mv.addObject("albaranEntrada", albaranEntrada); /*Si ponemos los mv.addObject por encima del mv no me los coge. Los coge la vista anterior*/
					mv.addObject("listadoTipos", listadoVariedades);
					mv.addObject("persona", p); //Si existe persona y cliente le paso el objeto a la vista
				} else {
					System.out.println("Nuevo Albarán - Error al crear el albarán de salida");
				}
				
				
			}
		}
		

	return mv;
	}
	
	/*********************************************************************************************************/
	/*********************************************************************************************************/
	/****************VISTA MODIFICAR ALBARAN E*****************/
	/*FUNCIONANDO*/
	/****************VISTA CREAR ALBARAN E*****************/ /*También sirve para esta vista*/
	/*BOTÓN DE ENVIAR PARA LAS MODIFICACIONES*/
	@RequestMapping ("/modificadoAlbaranEntrada")
	public ModelAndView modificadoAlbaranEntrada(HttpSession sesion,
			  @RequestParam (value="n_albaran") int nAlbaran,
			  @RequestParam (value ="n_socio") int nSocio,
			  @RequestParam (value = "fecha") String fecha,
			  @RequestParam (value = "n_factura") int nFactura) {
		
		Date fechaDate = DateUtils.parseFormato(fecha); /*Recibo un String, lo paso a tipo Date y lo incluyo en el objeto para hacer el update*/
		AlbaranEntrada albaranEntrada = new AlbaranEntrada (nAlbaran, nSocio, fechaDate, nFactura);
		ModelAndView mv = modificarAlbaranEntrada(sesion, nAlbaran); /*CUIDADO!!!!!!*/
		
			boolean modificado = daoae.update(albaranEntrada);
			if (!modificado){
				//Mandamos otra vez a la vista de modificarAlbaran, con el mismo albaran
				System.out.println("No se ha podido modificar el albarán de salida");
			}else{
				mv = listarAlbaranEntrada(sesion);
				System.out.println("Se ha modificado el albarán de salida");
			}
		
	return mv;
	}
	
	/*FUNCIONANDO*/
	/*Borrar una línea del acumulador*/
	@RequestMapping ("/eliminarAculumadorE")
	public ModelAndView eliminarAcumuladorE(HttpSession sesion,
			  @RequestParam (value="id_linea_e") int idLinea,
			  @RequestParam (value = "n_albaran") int nAlbaran) { //Recupero el idLinea de la línea que quiero borrar
	
	
		daole.delete(idLinea); //Borro la línea.
		ModelAndView mv = modificarAlbaranEntrada(sesion, nAlbaran);

	return mv;
	}
	
	/*FUNCIONANDO*/
	/*Añadir línea de albarán*/
	@RequestMapping ("/addAcumuladorE")
	public ModelAndView addAcumuladorE(HttpSession sesion,
			  @RequestParam (value="n_albaran") int nAlbaran,
			  @RequestParam (value = "tipoCereza") String tipo,
			  @RequestParam (value = "peso")double peso) { //Te tendría que poner el precio de caja por defecto al seleccionar un tipo de variedad//Javascript
		
		Variedades v = daov.read(tipo);
		
		LineaAlbaranEntrada lae = new LineaAlbaranEntrada (nAlbaran,-1,tipo, peso, v.getPrecioKg());
		
		boolean lineaCreada = daole.create(lae); //Creas una línea y compruebas con el boolean
		if (!lineaCreada){
			System.out.println("No se ha podido crear la línea de albaran salida");
		} else {
			System.out.println("La línea de albaran salida ha sido creada");
		}
		ModelAndView mv =  modificarAlbaranEntrada(sesion,nAlbaran); /*Mandas otra vez a la vista de modificarAlbaran para que prosiga asignando líneas o no*/
		/*Cuando añadimos una línea también tendríamos que enviar información de los campos que ha modificado el cliente como puede ser fecha*/
		return mv;
	}
	/*********************************************************************************************************/
	/*********************************************************************************************************/
	
	
}
